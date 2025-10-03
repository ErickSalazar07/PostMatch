package com.example.postmatch.data.datasource.services

import com.example.postmatch.data.dtos.PartidoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PartidoRetrofitService {
    @GET("/Partidos")
    suspend fun getAllPartidos(): List<PartidoDto>
    @GET("/Partidos/{id}")
    suspend fun getPartidoById(@Path("id") id: Int): PartidoDto
}