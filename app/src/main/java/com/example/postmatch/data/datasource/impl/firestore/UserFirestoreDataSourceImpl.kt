package com.example.postmatch.data.datasource.impl.firestore

import android.util.Log
import androidx.compose.animation.core.snap
import com.example.postmatch.data.datasource.UsuarioRemoteDataSource
import com.example.postmatch.data.dtos.RegisterUserDto
import com.example.postmatch.data.dtos.ReviewDto
import com.example.postmatch.data.dtos.UpdateUserDto
import com.example.postmatch.data.dtos.UsuarioDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


import jakarta.inject.Inject

class UserFirestoreDataSourceImpl @Inject constructor(private val db: FirebaseFirestore): UsuarioRemoteDataSource {

    override suspend fun getAllUsuarios(): List<UsuarioDto> {
        val snapshot = db.collection("users").get().await()
        return snapshot.documents.map { doc ->
            val user = doc.toObject(UsuarioDto::class.java)
            user?.copy(id = doc.id) ?: throw Exception("Usuario not found")
        }
    }

    override suspend fun getUsuarioById(id: String): UsuarioDto {
        val docRef = db.collection("users").document(id)
        val respuesta = docRef.get().await()
        return respuesta.toObject(UsuarioDto::class.java) ?: throw Exception("No se pudo obtener el usuario")
    }

    override suspend fun getReviewsByUsuarioId(idUsuario: String): List<ReviewDto> {
        val snapshot = db.collection("reviews").whereEqualTo("idUsuario", idUsuario).get().await()
        return snapshot.documents.map { doc ->
            val review = doc.toObject(ReviewDto::class.java)
            review?.copy(id = doc.id) ?: throw Exception("Review not found")
        }
    }

    override suspend fun registerUser(registerUserDto: RegisterUserDto, userId: String) {
        val docRef = db.collection("users").document(userId)
        docRef.set(registerUserDto).await()
    }

    override suspend fun updateUser(
        userId: String,
        updateUserDto: UpdateUserDto
    ) {
        val docRef = db.collection("users").document(userId)
        try {
            docRef.update(
                mapOf(
                    "nombre" to updateUserDto.nombre,
                    "email" to updateUserDto.email,
                    "password" to updateUserDto.password
                )
            ).await()
        } catch (e: Exception) {
            throw Exception("Error al actualizar el usuario: ${e.message}")
        }
    }

}

