package com.example.postmatch.data.datasource.impl.firestore

import com.example.postmatch.data.datasource.UsuarioRemoteDataSource
import com.example.postmatch.data.dtos.RegisterUserDto
import com.example.postmatch.data.dtos.ReviewDto
import com.example.postmatch.data.dtos.UpdateUserDto
import com.example.postmatch.data.dtos.UsuarioDto
import com.google.firebase.firestore.FieldValue
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

    override suspend fun getUsuarioById(id: String, idUsuarioActual: String): UsuarioDto {
        val docRef = db.collection("users").document(id)
        val respuesta = docRef.get().await()
        val user = respuesta.toObject(UsuarioDto::class.java) ?: throw Exception("No se pudo obtener el usuario")

        val followerDoc = db.collection("users").document(id).collection("followers").document(idUsuarioActual).get().await()

        val exists = followerDoc.exists()

        user.followed = exists

        return user
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

    override suspend fun updateFotoPerfilById(idUsuario: String,fotoPerfilUrl: String) {
        val docRef = db.collection("users").document(idUsuario)
        try {
            docRef.update(
                mapOf(
                    "fotoPerfil" to fotoPerfilUrl
                )
            ).await()
        } catch(e: Exception) {
            throw Exception("Error al actualizar el usuario: ${e.message}")
        }
    }

    override suspend fun seguirTantoDejarDeSeguirUsuario(idUsuarioActual: String, idUsuarioSeguir: String){
        val usuarioActualRef = db.collection("users").document(idUsuarioActual)
        val usuarioSeguirRef = db.collection("users").document(idUsuarioSeguir)

        val followingRef = usuarioActualRef.collection("following").document(idUsuarioSeguir)
        val followerRef = usuarioSeguirRef.collection("followers").document(idUsuarioActual)

        db.runTransaction { transaction ->
            val followingDoc = transaction.get(followingRef)

            if(followingDoc.exists()){

                transaction.delete(followingRef)
                transaction.delete(followerRef)

                transaction.update(usuarioActualRef ,"numFollowed", FieldValue.increment(-1))
                transaction.update(usuarioSeguirRef ,"numFollowers", FieldValue.increment(-1))

            }
            else{

                transaction.set(followingRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.set(followerRef, mapOf("timestamp" to FieldValue.serverTimestamp()))

                transaction.update(usuarioActualRef ,"numFollowed", FieldValue.increment(1))
                transaction.update(usuarioSeguirRef ,"numFollowers", FieldValue.increment(1))

            }
        }
    }
    override suspend fun getFollowersOfUserById(idUsuario: String): List<UsuarioDto> {
        val followersSnapshot = db.collection("users")
            .document(idUsuario)
            .collection("following")
            .get()
            .await()

        val followerIds = followersSnapshot.documents.map { it.id }


        if (followerIds.isEmpty()) return emptyList()

        val followersList = mutableListOf<UsuarioDto>()

        for (followerId in followerIds) {
            val followerDoc = db.collection("users").document(followerId).get().await()
            val followerUser = followerDoc.toObject(UsuarioDto::class.java)
            followerUser?.let {
                it.id = followerDoc.id
                followersList.add(it)
            }
        }

        return followersList

    }
}

