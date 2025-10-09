package com.example.postmatch.data.repository

import coil.network.HttpException
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.datasource.impl.ReviewRetrofitDataSourceImpl
import com.example.postmatch.data.dtos.CreateReviewDto
import com.example.postmatch.data.dtos.toReviewInfo
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


    suspend fun createReview(titulo: String, descripcion: String, fecha: Date, idUsuario: Int, idPartido: Int): Result<Unit>{
        return try {
            val createReviewDto = CreateReviewDto(titulo, descripcion, fecha, idUsuario, idPartido)
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

    suspend fun updateReview(
        idReview: String,
        titulo: String,
        descripcion: String,
        fecha: Date,
        idUsuario: Int,
        idPartido: Int
    ): Result<Unit> {
        return try {
            val updateReviewDto = CreateReviewDto(titulo, descripcion, fecha, idUsuario, idPartido)
            reviewRemoteDataSource.updateReview(idReview, updateReviewDto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}