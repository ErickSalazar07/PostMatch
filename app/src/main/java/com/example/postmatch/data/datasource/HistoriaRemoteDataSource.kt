package com.example.postmatch.data.datasource

import android.graphics.Bitmap
import android.net.Uri
import com.example.postmatch.data.dtos.HistoriaDTO
import javax.inject.Inject

interface HistoriaRemoteDataSource{
    suspend fun getHistorias(idUsuario : String) : List<HistoriaDTO>

    suspend fun subirImagenStorage(imageUri: Uri): String

    suspend fun guardarHistoriaFirestore(idUsuario: String, historiaDTO: HistoriaDTO)
}