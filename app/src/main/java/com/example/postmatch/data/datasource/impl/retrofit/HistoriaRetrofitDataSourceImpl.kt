package com.example.postmatch.data.datasource.impl.retrofit

import android.net.Uri
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
    override suspend fun subirImagenStorage(imageUri: Uri): String{
        TODO("Not yet implemented")
    }
    override suspend fun guardarHistoriaFirestore(idUsuario: String, historiaDTO: HistoriaDTO): Unit{
        TODO("Not yet implemented")

    }
}