package com.example.postmatch.data.datasource.impl

import com.example.postmatch.data.datasource.PartidoRemoteDataSource
import com.example.postmatch.data.datasource.services.PartidoRetrofitService
import com.example.postmatch.data.dtos.PartidoDto
import com.example.postmatch.data.dtos.ReviewDto
import javax.inject.Inject

class PartidoRetrofitDataSourceImpl @Inject constructor(
    val service: PartidoRetrofitService
): PartidoRemoteDataSource {
    override suspend fun getAllPartidos(): List<PartidoDto> {
        return service.getAllPartidos()
    }
    override suspend fun getPartidoById(id: String): PartidoDto {
        return service.getPartidoById(id.toInt())
    }

    override suspend fun getReviewsByPartidoId(idPartido: String): List<ReviewDto> {
        return service.getReviewsByPartidoId(idPartido.toInt())
    }
}