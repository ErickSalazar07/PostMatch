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

   /*
    override suspend fun getAllUsuarios(): List<UsuarioDto> {
        return try {
            // 🔹 Obtener todos los documentos de la colección "users"
            val snapshot = db.collection("users").get().await()

            // 🔹 Convertir cada documento en un UsuarioDto
            snapshot.documents.mapNotNull { it.toObject(UsuarioDto::class.java) }

        } catch (e: Exception) {
            throw Exception("Error al obtener los usuarios: ${e.message}")
        }
    }

    */

    override suspend fun getAllUsuarios(): List<UsuarioDto> {
        return try {
            val snapshot = db.collection("users").get().await()

            val usuarios = snapshot.documents.mapNotNull { it.toObject(UsuarioDto::class.java) }

            Log.d("FirestoreDataSource", "✅ Se obtuvieron ${usuarios.size} usuarios de Firestore")
            usuarios.forEach { user ->
                Log.d("FirestoreDataSource", "➡️ Usuario: ${user.nombre} | Email: ${user.email}")
            }

            usuarios
        } catch (e: Exception) {
            Log.e("FirestoreDataSource", "❌ Error al obtener los usuarios: ${e.message}")
            throw e
        }
    }


    override suspend fun getUsuarioById(id: String): UsuarioDto {
        val docRef = db.collection("users").document(id)
        val respuesta = docRef.get().await()
        return respuesta.toObject(UsuarioDto::class.java) ?: throw Exception("No se pudo obtener el usuario")
    }

    override suspend fun getReviewsByUsuarioId(idUsuario: String): List<ReviewDto> {
        TODO("Not yet implemented")
    }

    override suspend fun registerUser(
        registerUserDto: RegisterUserDto,
        userId: String) {
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


