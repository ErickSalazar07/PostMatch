package com.example.postmatch.data.datasource.impl.retrofit

import com.example.postmatch.data.datasource.HistoriaRemoteDataSource
import com.example.postmatch.data.datasource.services.HistoriaRetrofitService
import com.example.postmatch.data.dtos.HistoriaDTO
import javax.inject.Inject

class HistoriaRetrofitDataSourceImpl @Inject constructor(
    val service : HistoriaRetrofitService
) : HistoriaRemoteDataSource{
    override suspend fun getHistorias(idUsuario: String): List<HistoriaDTO> {
        TODO("Not yet implemented")
    }
}