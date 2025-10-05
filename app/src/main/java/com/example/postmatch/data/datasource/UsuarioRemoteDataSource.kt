package com.example.postmatch.data.datasource

import com.example.postmatch.data.dtos.ReviewDto
import com.example.postmatch.data.dtos.UsuarioDto

interface UsuarioRemoteDataSource {
    suspend fun getAllUsuarios(): List<UsuarioDto>
    suspend fun getUsuarioById(id: String): UsuarioDto
    suspend fun getReviewsByUsuarioId(idUsuario: String): List<ReviewDto>
}