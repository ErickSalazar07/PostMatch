package com.example.postmatch.data.datasource.impl.retrofit

import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.datasource.ReviewRemoteDataSource
import com.example.postmatch.data.datasource.services.ReviewRetrofitService
import com.example.postmatch.data.dtos.CreateReviewDto
import com.example.postmatch.data.dtos.ReviewDto
import com.example.postmatch.data.dtos.UpdateReviewDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewRetrofitDataSourceImpl @Inject constructor(
    val service: ReviewRetrofitService
) : ReviewRemoteDataSource {
    override suspend fun getAllReviews(): List<ReviewDto> {
        return service.getAllReviews()
    }

    override suspend fun getReviewById(id: String): ReviewDto {
        return service.getReviewById(id.toInt())
    }

    override suspend fun getReviewsByUser(userId: String): List<ReviewDto> {

        return service.getAllReviews().filter { it.idUsuario == userId }
    }

    override suspend fun sendOrDeleteLike(reviewId: String, userId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun listenReviews(): Flow<List<ReviewDto>> {
        TODO("Not yet implemented")
    }


    override suspend fun createReview(review: CreateReviewDto) {
        service.createReview(review)
    }

    override suspend fun deleteReview(id: String) {
        service.deleteReview(id.toInt())
    }

    override suspend fun updateReview(id: String, review: UpdateReviewDto) {
        service.updateReview(id.toInt(), review)
    }
}