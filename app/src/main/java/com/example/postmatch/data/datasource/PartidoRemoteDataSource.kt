package com.example.postmatch.data.datasource

import com.example.postmatch.data.dtos.PartidoDto


interface PartidoRemoteDataSource {
    suspend fun getAllPartidos(): List<PartidoDto>
    suspend fun getPartidoById(id: String): PartidoDto
}