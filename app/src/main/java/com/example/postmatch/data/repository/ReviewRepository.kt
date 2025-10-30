package com.example.postmatch.data.repository

import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.example.postmatch.data.datasource.impl.firestore.PartidoFirestoreDataSourceImpl
import com.example.postmatch.data.datasource.impl.firestore.ReviewFirestoreDataSourceImpl
import com.example.postmatch.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.postmatch.data.dtos.CreateReviewDto
import com.example.postmatch.data.dtos.PartidoCreateDto
import com.example.postmatch.data.dtos.UpdateReviewDto
import com.example.postmatch.data.dtos.UsuarioCreateDto
import com.example.postmatch.data.dtos.toReviewInfo
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import java.util.Date
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val reviewRemoteDataSource: ReviewFirestoreDataSourceImpl,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val usuarioRemoteDataSource: UserFirestoreDataSourceImpl,
    private val partidoRemoteDataSource: PartidoFirestoreDataSourceImpl
) {

    suspend fun getReviews(): Result<List<ReviewInfo>> {
        return try {
            val reviews = reviewRemoteDataSource.getAllReviews()
            val reviewsInfo = reviews.map { it.toReviewInfo() }
            Result.success(reviewsInfo)
        } catch (_: HttpException) {
            Result.failure(Exception("Error de comunicación con el servidor al obtener las reseñas."))
        } catch (_: FirebaseFirestoreException) {
            Result.failure(Exception("Error al acceder a las reseñas en Firestore."))
        } catch (_: IOException) {
            Result.failure(Exception("No hay conexión a internet. Intenta nuevamente."))
        } catch (_: Exception) {
            Result.failure(Exception("Error interno al obtener la lista de reseñas."))
        }
    }

    suspend fun getReviewById(id: String): Result<ReviewInfo> {
        return try {
            val reviewDto = reviewRemoteDataSource.getReviewById(id)
            Result.success(reviewDto.toReviewInfo())
        } catch (_: HttpException) {
            Result.failure(Exception("Error de comunicación con el servidor al obtener la reseña."))
        } catch (_: FirebaseFirestoreException) {
            Result.failure(Exception("No se encontró la reseña en la base de datos."))
        } catch (_: IOException) {
            Result.failure(Exception("Verifica tu conexión a internet e intenta de nuevo."))
        } catch (_: Exception) {
            Result.failure(Exception("Error interno al recuperar la información de la reseña."))
        }
    }

    suspend fun createReview(createReviewDto: CreateReviewDto): Result<Unit> {
        return try {
            createReviewDto.idUsuario = authRemoteDataSource.currentUser?.uid ?: ""
            val partidoDto = partidoRemoteDataSource.getPartidoById(createReviewDto.idPartido)
            val usuarioDto = usuarioRemoteDataSource.getUsuarioById(createReviewDto.idUsuario, createReviewDto.idUsuario)

            val createPartidoDto = PartidoCreateDto(partidoDto.partidoFotoUrl, partidoDto.fecha)
            val createUsuarioDto = UsuarioCreateDto(usuarioDto?.nombre, usuarioDto?.email, usuarioDto?.fotoPerfilUrl)

            createReviewDto.partido = createPartidoDto
            createReviewDto.usuario = createUsuarioDto

            reviewRemoteDataSource.createReview(createReviewDto)
            Result.success(Unit)
        } catch (_: HttpException) {
            Result.failure(Exception("No se pudo crear la reseña debido a un error de red."))
        } catch (_: FirebaseFirestoreException) {
            Result.failure(Exception("Ocurrió un error al guardar la reseña en Firestore."))
        } catch (_: IOException) {
            Result.failure(Exception("Verifica tu conexión a internet antes de crear la reseña."))
        } catch (_: Exception) {
            Result.failure(Exception("Error interno al intentar crear la reseña."))
        }
    }

    suspend fun deleteReviewById(idReview: String): Result<Unit> {
        return try {
            reviewRemoteDataSource.deleteReview(idReview)
            Result.success(Unit)
        } catch (_: HttpException) {
            Result.failure(Exception("Error de comunicación con el servidor al eliminar la reseña."))
        } catch (_: FirebaseFirestoreException) {
            Result.failure(Exception("No se pudo eliminar la reseña de la base de datos."))
        } catch (_: IOException) {
            Result.failure(Exception("No hay conexión a internet. Intenta eliminar la reseña más tarde."))
        } catch (_: Exception) {
            Result.failure(Exception("Error interno al eliminar la reseña."))
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

            reviewRemoteDataSource.updateReview(review.idReview, updateReviewDto)
            Result.success(Unit)
        } catch (_: HttpException) {
            Result.failure(Exception("No se pudo actualizar la reseña debido a un error de comunicación."))
        } catch (_: FirebaseFirestoreException) {
            Result.failure(Exception("Error al actualizar la reseña en Firestore."))
        } catch (_: IOException) {
            Result.failure(Exception("Revisa tu conexión a internet antes de actualizar la reseña."))
        } catch (_: Exception) {
            Result.failure(Exception("Error interno al actualizar la reseña."))
        }
    }

    suspend fun sendOrDeleteLike(reviewId: String, userId: String): Result<Unit> {
        return try {
            reviewRemoteDataSource.sendOrDeleteLike(reviewId, userId)
            Result.success(Unit)
        } catch (_: HttpException) {
            Result.failure(Exception("Error de comunicación al intentar actualizar los 'me gusta'."))
        } catch (_: FirebaseFirestoreException) {
            Result.failure(Exception("No se pudo registrar el 'me gusta' en Firestore."))
        } catch (_: IOException) {
            Result.failure(Exception("Parece que no hay conexión a internet."))
        } catch (_: Exception) {
            Result.failure(Exception("Error interno al actualizar los 'me gusta'."))
        }
    }

    suspend fun getReviewsFromFollowedUsers(): Result<List<ReviewInfo>> {
        return try {
            val currentUserId = authRemoteDataSource.currentUser?.uid
                ?: return Result.failure(Exception("Usuario no autenticado."))

            val followedUsers = usuarioRemoteDataSource.getFollowersOfUserById(currentUserId)

            if (followedUsers.isEmpty()) {
                return Result.success(emptyList())
            }

            val allReviews = followedUsers.flatMap { user ->
                reviewRemoteDataSource.getReviewsByUser(user.id).map { it.toReviewInfo() }
            }

            Result.success(allReviews)
        } catch (_: HttpException) {
            Result.failure(Exception("Error de comunicación con el servidor al obtener las reseñas de usuarios seguidos."))
        } catch (_: FirebaseFirestoreException) {
            Result.failure(Exception("Ocurrió un error al acceder a las reseñas en Firestore."))
        } catch (_: IOException) {
            Result.failure(Exception("No hay conexión a internet. Intenta nuevamente."))
        } catch (_: Exception) {
            Result.failure(Exception("Error interno al obtener reseñas de usuarios seguidos."))
        }
    }

    suspend fun getReviewsOnline(): Flow<List<ReviewInfo>> {
        return reviewRemoteDataSource.listenReviews().map { reviews ->
            reviews.map { it.toReviewInfo() }
        }
    }
}
