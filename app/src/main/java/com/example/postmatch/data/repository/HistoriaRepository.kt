package com.example.postmatch.data.repository

import android.util.Log
import coil.network.HttpException
import com.example.postmatch.data.Historia
import com.example.postmatch.data.datasource.HistoriaRemoteDataSource
import com.example.postmatch.data.datasource.impl.firestore.HistoriaFirestoreDataSourceImpl
import com.example.postmatch.data.dtos.toHistoria
import javax.inject.Inject

class HistoriaRepository @Inject constructor(
    private val historiaRemoteDataSource: HistoriaFirestoreDataSourceImpl
) {

    suspend fun getHistorias(idUsuario : String) : Result<List<Historia>> {
        return try {
            Log.d("HistoriaScreen", " HistoriaRepository - getHistorias() entrada try")
            val historias = historiaRemoteDataSource.getHistorias(idUsuario)
            val historiasDevolver = historias.map{ historia ->
                Log.d("HistoriaScreen", " HistoriaRepository - getHistorias() entrada map: ${historia}")
                historia.toHistoria()
                //Log.d("HistoriaScreen", " HistoriaRepository - getHistorias() entrada map2: ${historia}")
            }.filter { it.horasHistoria < 25 }

            Log.d("HistoriaScreen", " HistoriaRepository - getHistorias() entrada if: ${historiasDevolver}")

            Result.success(historiasDevolver)
        }
        catch(e : HttpException){
            Log.d("HistoriaScreen", " HistoriaRepository - getHistorias() entrada catch1: ${e.response.code}")

            Result.failure(e)
        }
        catch (e : Exception){
            Log.d("HistoriaScreen", " HistoriaRepository - getHistorias() entrada catch2: ${e.message}")
            Result.failure(e)
        }
    }
}