package com.example.postmatch.data.datasource.impl.firestore


import android.util.Log
import com.example.postmatch.data.datasource.HistoriaRemoteDataSource
import com.example.postmatch.data.dtos.HistoriaDTO
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HistoriaFirestoreDataSourceImpl @Inject constructor(
    private val db : FirebaseFirestore
): HistoriaRemoteDataSource {

    override suspend fun getHistorias(idUsuario: String): List<HistoriaDTO> {
        val snapshot =
            db.collection("users").document(idUsuario).collection("historia").get().await()

        Log.d("HistoriaDataSource", "=== DEBUG DETALLADO DE SNAPSHOT ===")
        Log.d("HistoriaDataSource", "Total documentos: ${snapshot.documents.size}")
        snapshot.documents.forEach { doc ->
            Log.d("HistoriaDataSource", """
        📋 Documento ID: ${doc.id}
        📝 Datos completos: ${doc.data}
        🔍 Campos:
           - idUsuario: ${doc.getString("idUsuario")}
           - imagenHistoria: ${doc.getString("imagenHistoria")}
           - horasHistoria: ${doc.getTimestamp("timestamp")}
        """.trimIndent())
        }
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(HistoriaDTO::class.java)?.copy(idHistoria = doc.id)
        }
    }

}