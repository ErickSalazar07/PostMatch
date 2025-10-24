package com.example.postmatch.data.datasource

import com.example.postmatch.data.dtos.HistoriaDTO
import javax.inject.Inject

interface HistoriaRemoteDataSource{
    suspend fun getHistorias(idUsuario : String) : List<HistoriaDTO>
}