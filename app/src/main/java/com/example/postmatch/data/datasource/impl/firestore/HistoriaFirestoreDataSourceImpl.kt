package com.example.postmatch.data.datasource.impl.firestore


import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.example.postmatch.data.datasource.HistoriaRemoteDataSource
import com.example.postmatch.data.dtos.HistoriaDTO
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class HistoriaFirestoreDataSourceImpl @Inject constructor(
    private val db : FirebaseFirestore,
    private val storage: FirebaseStorage
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

    override suspend fun subirImagenStorage(imageUri: Uri): String {
        return suspendCoroutine { continuation ->
            try {
                // Nombre único para la imagen
                val nombreArchivo = "historia_${System.currentTimeMillis()}.jpg"
                val storageRef = storage.reference
                    .child("historias")
                    .child(nombreArchivo)

                // Subir la URI directamente
                val uploadTask = storageRef.putFile(imageUri)

                uploadTask.addOnSuccessListener {
                    // Obtener URL de descarga
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        continuation.resume(uri.toString())
                    }.addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }

            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

    // Guardar datos en Firestore
    override suspend fun guardarHistoriaFirestore(
        idUsuario: String,
        historiaDTO: HistoriaDTO
    ) {
        return suspendCoroutine { continuation ->
            try {
                // Ruta: users/{idUsuario}/historias/{idGenerado}
                val historiaRef = db
                    .collection("users")
                    .document(idUsuario)
                    .collection("historias")
                    .document()

                // Datos a guardar
                val historiaData = hashMapOf(
                    "idUsuario" to historiaDTO.idUsuario,
                    "imagenHistoria" to historiaDTO.imagenHistoria,
                    "timestamp" to historiaDTO.timestamp
                )

                historiaRef.set(historiaData)
                    .addOnSuccessListener {
                        continuation.resume(Unit)
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }

            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

}