package com.example.postmatch.data.datasource.services

import com.example.postmatch.data.dtos.CreateReviewDto
import com.example.postmatch.data.dtos.ReviewDto
import com.google.android.gms.common.api.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewRetrofitService {

    @GET("/Reviews")
    suspend fun getAllReviews(): List<ReviewDto>

    @GET("/Reviews{id}")
    suspend fun getReviewById(@Path("id") id: Int): ReviewDto

    @GET("/Reviews/User/{idUsuario}")
    suspend fun getReviewsByUser(@Path("idUsuario") idUsuario: Int): List<ReviewDto>

    @POST("/Reviews")
    suspend fun createReview(@Body review: CreateReviewDto): Unit

    @DELETE("/Reviews/{id}")
    suspend fun deleteReview(@Path("id") id: Int): Unit

    @PUT("/Review/{id}")
    suspend fun updateReview(@Path("id")id: Int, @Body review: CreateReviewDto): Unit
}