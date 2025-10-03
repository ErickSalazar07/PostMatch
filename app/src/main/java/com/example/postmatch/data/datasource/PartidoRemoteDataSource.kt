package com.example.postmatch.data.datasource

import com.example.postmatch.data.dtos.PartidoDto
import com.example.postmatch.data.dtos.ReviewDto


interface PartidoRemoteDataSource {
    suspend fun getAllPartidos(): List<PartidoDto>
    suspend fun getPartidoById(id: String): PartidoDto
    suspend fun getReviewsByPartidoId(idPartido: String): List<ReviewDto>
}