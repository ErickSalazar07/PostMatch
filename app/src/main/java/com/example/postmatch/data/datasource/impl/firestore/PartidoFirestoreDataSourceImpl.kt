package com.example.postmatch.data.datasource.impl.firestore

import com.example.postmatch.data.datasource.PartidoRemoteDataSource
import com.example.postmatch.data.dtos.PartidoDto
import com.example.postmatch.data.dtos.ReviewDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PartidoFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
): PartidoRemoteDataSource {
    override suspend fun getAllPartidos(): List<PartidoDto> {
        val snapshot = db.collection("partidos").get().await()
        return snapshot.documents.map { doc ->
            val partido = doc.toObject(PartidoDto::class.java)
            partido?.copy(id = doc.id) ?: throw Exception("Partido not found")
        }
    }

    override suspend fun getPartidoById(id: String): PartidoDto {
        val docRef = db.collection("partidos").document(id)
        val respuesta = docRef.get().await()
        return respuesta.toObject(PartidoDto::class.java) ?: throw Exception("No se pudo obtener el partido")
    }

    override suspend fun getReviewsByPartidoId(idPartido: String): List<ReviewDto> {
        TODO("Not yet implemented")
    }
}