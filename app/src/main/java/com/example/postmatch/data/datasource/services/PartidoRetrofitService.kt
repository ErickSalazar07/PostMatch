package com.example.postmatch.data.datasource.services

import com.example.postmatch.data.dtos.PartidoDto
import com.example.postmatch.data.dtos.ReviewDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PartidoRetrofitService {
    @GET("/Partidos")
    suspend fun getAllPartidos(): List<PartidoDto>
    @GET("/Partidos/{id}")
    suspend fun getPartidoById(@Path("id") id: Int): PartidoDto

    @GET("/Partidos/{idPartido}/reviews")
    suspend fun getReviewsByPartidoId(@Path("idPartido") idPartido: Int): List<ReviewDto>
}