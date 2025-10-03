package com.example.postmatch.data.datasource.impl

import com.example.postmatch.data.datasource.ReviewRemoteDataSource
import com.example.postmatch.data.datasource.services.ReviewRetrofitService
import com.example.postmatch.data.dtos.CreateReviewDto
import com.example.postmatch.data.dtos.ReviewDto
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

    override suspend fun createReview(review: CreateReviewDto) {
        service.createReview(review)
    }

    override suspend fun deleteReview(id: String) {
        service.deleteReview(id.toInt())
    }

    override suspend fun updateReview(id: String, review: CreateReviewDto) {
        service.updateReview(id.toInt(), review)
    }
}