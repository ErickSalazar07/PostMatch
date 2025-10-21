package com.example.postmatch.data.repository

import android.util.Log
import coil.network.HttpException
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.example.postmatch.data.datasource.impl.retrofit.ReviewRetrofitDataSourceImpl
import com.example.postmatch.data.datasource.impl.firestore.PartidoFirestoreDataSourceImpl
import com.example.postmatch.data.datasource.impl.firestore.ReviewFirestoreDataSourceImpl
import com.example.postmatch.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.postmatch.data.dtos.CreateReviewDto
import com.example.postmatch.data.dtos.PartidoCreateDto
import com.example.postmatch.data.dtos.UpdateReviewDto
import com.example.postmatch.data.dtos.UsuarioCreateDto
import com.example.postmatch.data.dtos.UsuarioDto
import com.example.postmatch.data.dtos.toReviewInfo
import com.example.postmatch.ui.Screens.partidos.ResultadoPartidoCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val reviewRemoteDataSource: ReviewFirestoreDataSourceImpl,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val usuarioRemoteDataSource: UserFirestoreDataSourceImpl,
    private val partidoRemoteDataSource: PartidoFirestoreDataSourceImpl
){
    suspend fun getReviews(): Result<List<ReviewInfo>>{
        return try {
            val reviews = reviewRemoteDataSource.getAllReviews()
            val reviewsInfo = reviews.map { it.toReviewInfo() }
            Result.success(reviewsInfo)

        } catch (e: HttpException){
            e.response.code
            Result.failure(e)
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReviewById(id: String): Result<ReviewInfo> {
        return try {
            val reviewDto = reviewRemoteDataSource.getReviewById(id)
            Result.success(reviewDto.toReviewInfo())
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun createReview(createReviewDto: CreateReviewDto): Result<Unit>{
        return try {
            createReviewDto.idUsuario = authRemoteDataSource.currentUser?.uid ?: ""
            val partidoDto = partidoRemoteDataSource.getPartidoById(createReviewDto.idPartido)
            val usuarioDto = usuarioRemoteDataSource.getUsuarioById(createReviewDto.idUsuario, createReviewDto.idUsuario)
            val createPartidoDto = PartidoCreateDto(partidoDto.partidoFotoUrl, partidoDto.fecha)
            val createUsuarioDto = UsuarioCreateDto(usuarioDto.nombre, usuarioDto.email, usuarioDto.fotoPerfilUrl)
            createReviewDto.partido = createPartidoDto
            createReviewDto.usuario = createUsuarioDto
            reviewRemoteDataSource.createReview(createReviewDto)
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun deleteReviewById(idReview: String): Result<Unit>{
        return try {
            val result = reviewRemoteDataSource.deleteReview(idReview)
            Result.success(Unit)
        } catch (e: HttpException){
            e.response.code
            Result.failure(e)
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateReview(review: ReviewInfo): Result<Unit> {
        return try {
            val updateReviewDto = UpdateReviewDto(
                titulo = review.titulo,
                descripcion = review.descripcion,
                fecha = Date(),
                calificacion = review.calificacion,
                idUsuario = review.usuarioId,
                idPartido = review.partidoId
            )
            Log.d("ReviewRepository", "ReviewDto: ${review.idReview}")
            Log.d("ReviewRepository", "ReviewDto: ${updateReviewDto.titulo}")
            Log.d("ReviewRepository", "ReviewDto: ${updateReviewDto.descripcion}")
            Log.d("ReviewRepository", "ReviewDto: ${updateReviewDto.fecha}")
            Log.d("ReviewRepository", "ReviewDto: ${updateReviewDto.calificacion}")
            Log.d("ReviewRepository", "ReviewDto: ${updateReviewDto.idUsuario}")
            Log.d("ReviewRepository", "ReviewDto: ${updateReviewDto.idPartido}")
            reviewRemoteDataSource.updateReview(review.idReview, updateReviewDto)
            Result.success(Unit)
        } catch (e: HttpException) {
            Log.d("ReviewRepository", "ReviewDto HttpException: ${e.toString()}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.d("ReviewRepository", "ReviewDto HttpException: ${e.toString()}")
            Result.failure(e)
        }
    }




    suspend fun  sendOrDeleteLike(reviewId: String, userId: String): Result<Unit>{

      return  try {
          reviewRemoteDataSource.sendOrDeleteLike(reviewId,userId)
          Result.success(Unit)
      }catch (e: Exception){

          Result.failure(e)
      }

    }


    suspend fun getReviewsFromFollowedUsers(): Result<List<ReviewInfo>> {
        return try {
            val currentUserId = authRemoteDataSource.currentUser?.uid
            Log.d("DEBUG_REVIEWS_FOLLOW", "Usuario actual UID = $currentUserId")

            if (currentUserId == null) {
                Log.e("DEBUG_REVIEWS_FOLLOW", "❌ Usuario no autenticado")
                return Result.failure(Exception("Usuario no autenticado"))
            }

            // 🔹 IMPORTANTE: cambiar según si quieres los SEGUIDOS o los SEGUIDORES
            Log.d("DEBUG_REVIEWS_FOLLOW", "Obteniendo usuarios que SIGUE el usuario actual...")
            val followedUsers = usuarioRemoteDataSource.getFollowersOfUserById(currentUserId)

            Log.d("DEBUG_REVIEWS_FOLLOW", "Usuarios seguidos obtenidos = ${followedUsers.size}")

            val followedUserIds = followedUsers.map { it.id }
            Log.d("DEBUG_REVIEWS_FOLLOW", "IDs de usuarios seguidos: $followedUserIds")

            if (followedUserIds.isEmpty()) {
                Log.w("DEBUG_REVIEWS_FOLLOW", "⚠️ El usuario no sigue a nadie.")
                return Result.success(emptyList())
            }

            val allReviews = mutableListOf<ReviewInfo>()

            for (userId in followedUserIds) {
                try {
                    Log.d("DEBUG_REVIEWS_FOLLOW", "🔍 Buscando reseñas del usuario $userId...")
                    val userReviews = reviewRemoteDataSource.getReviewsByUser(userId)
                    Log.d("DEBUG_REVIEWS_FOLLOW", "✅ Encontradas ${userReviews.size} reseñas de $userId")

                    allReviews.addAll(userReviews.map { it.toReviewInfo() })
                } catch (inner: Exception) {
                    Log.e("DEBUG_REVIEWS_FOLLOW", "❌ Error al obtener reseñas del usuario $userId", inner)
                }
            }

            Log.d("DEBUG_REVIEWS_FOLLOW", "Total reseñas encontradas: ${allReviews.size}")
            Result.success(allReviews)

        } catch (e: Exception) {
            Log.e("DEBUG_REVIEWS_FOLLOW", "❌ Error general en getReviewsFromFollowedUsers", e)
            Result.failure(e)
        }
    }









    suspend fun getReviewsOnline(): Flow<List<ReviewInfo>>{

        return reviewRemoteDataSource.listenReviews().map { reviews ->
            reviews.map { it.toReviewInfo() }
        }
    }



}