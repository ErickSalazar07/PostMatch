package com.example.postmatch.data.datasource.impl.firestore

import com.example.postmatch.data.datasource.UsuarioRemoteDataSource
import com.example.postmatch.data.dtos.RegisterUserDto
import com.example.postmatch.data.dtos.ReviewDto
import com.example.postmatch.data.dtos.UsuarioDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


import jakarta.inject.Inject

class UserFirestoreDataSourceImpl @Inject constructor(private val db: FirebaseFirestore): UsuarioRemoteDataSource {
    override suspend fun getAllUsuarios(): List<UsuarioDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getUsuarioById(id: String): UsuarioDto {
        TODO("Not yet implemented")
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
}


