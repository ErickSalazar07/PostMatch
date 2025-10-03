package com.example.postmatch.data.datasource

import com.example.postmatch.data.ReviewInfo

interface ReviewRemoteDataSource {
    suspend fun getAllReviews(): List<ReviewInfo>
}