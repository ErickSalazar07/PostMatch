package com.example.postmatch.data.repository

import coil.network.HttpException
import com.example.postmatch.data.Historia
import com.example.postmatch.data.datasource.HistoriaRemoteDataSource
import com.example.postmatch.data.dtos.toHistoria
import javax.inject.Inject

class HistoriaRepository @Inject constructor(
    private val historiaRemoteDataSource: HistoriaRemoteDataSource
) {

    suspend fun getHistorias(idUsuario : String) : Result<List<Historia>> {
        return try {
            val historias = historiaRemoteDataSource.getHistorias(idUsuario)
            val historiasDevolver = historias.map{ historia ->
                historia.toHistoria()
            }.filter { it.horasHistoria < 25 }

            Result.success(historiasDevolver)
        }
        catch(e : HttpException){
            Result.failure(e)
        }
        catch (e : Exception){
            Result.failure(e)
        }
    }
}