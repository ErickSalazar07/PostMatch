package com.example.postmatch.data.repository

import android.util.Log
import coil.network.HttpException
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.datasource.impl.ReviewRetrofitDataSourceImpl
import com.example.postmatch.data.dtos.CreateReviewDto
import com.example.postmatch.data.dtos.UpdateReviewDto
import com.example.postmatch.data.dtos.toReviewInfo
import com.example.postmatch.ui.Screens.partidos.ResultadoPartidoCard
import java.util.Date
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val reviewRemoteDataSource: ReviewRetrofitDataSourceImpl
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


    /*

    suspend fun  sendOrDeleteLike(reviewId: String, userId: String): Result<Unit>{

      return  try {
          reviewRemoteDataSource.sendOrDeleteLike(reviewId,userId)
          Result.success(Unit)
      }catch (e: Exception){

          Result.failure(e)
      }

    }

     */
}