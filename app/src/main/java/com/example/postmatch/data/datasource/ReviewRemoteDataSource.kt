package com.example.postmatch.data.datasource

import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.dtos.CreateReviewDto
import com.example.postmatch.data.dtos.ReviewDto
import com.example.postmatch.data.dtos.UpdateReviewDto
import kotlinx.coroutines.flow.Flow

interface ReviewRemoteDataSource {
    suspend fun getAllReviews(): List<ReviewDto>
    suspend fun getReviewById(id: String): ReviewDto
    suspend fun createReview(review: CreateReviewDto): Unit
    suspend fun deleteReview(id: String): Unit

    suspend fun updateReview(id: String, review: UpdateReviewDto): Unit
    suspend fun getReviewsByUser(userId: String): List<ReviewDto>

    suspend fun sendOrDeleteLike(reviewId : String, userId: String): Unit
    suspend fun listenReviews(): Flow<List<ReviewDto>>



}