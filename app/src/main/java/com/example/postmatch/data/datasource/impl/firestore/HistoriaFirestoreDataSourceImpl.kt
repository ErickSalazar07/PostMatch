package com.example.postmatch.data.datasource.impl.firestore


import com.example.postmatch.data.datasource.HistoriaRemoteDataSource
import com.example.postmatch.data.dtos.HistoriaDTO
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HistoriaFirestoreDataSourceImpl @Inject constructor(
    private val db : FirebaseFirestore
): HistoriaRemoteDataSource {

    override suspend fun getHistorias(idUsuario: String): List<HistoriaDTO> {
        val snapshot = db.collection("users").document(idUsuario).collection("historia").get().await()

        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(HistoriaDTO::class.java)?.copy(idHistoria = doc.id)
        }
    }

}