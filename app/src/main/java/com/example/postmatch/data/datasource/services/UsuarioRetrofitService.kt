package com.example.postmatch.data.datasource.services

import com.example.postmatch.data.dtos.ReviewDto
import com.example.postmatch.data.dtos.UsuarioDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UsuarioRetrofitService {
    @GET("/Usuarios")
    suspend fun getAllUsuarios(): List<UsuarioDto>

    @GET("/Usuarios/{id}")
    suspend fun getUsuarioById(@Path("id") id: Int): UsuarioDto

    @GET("/Usuarios/{idUsuario}/reviews")
    suspend fun getReviewsByUsuarioId(@Path("idUsuario") idUsuario: Int): List<ReviewDto>
}