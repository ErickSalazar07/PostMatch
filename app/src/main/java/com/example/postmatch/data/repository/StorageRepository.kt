package com.example.postmatch.data.repository

import android.net.Uri
import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.example.postmatch.data.datasource.StorageRemoteDataSource
import javax.inject.Inject

class StorageRepository @Inject constructor(
    private val storage: StorageRemoteDataSource,
    private val auth: AuthRemoteDataSource
) {
    suspend fun uploadProfileImage(uri: Uri): Result<String> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Usuario no autenticado"))
            val path = "profileImages/$userId.jpg"
            val url = storage.uploadImage(path, uri)
            Result.success(url)
        } catch(e: Exception) {
            Result.failure(Exception("Error al subir la imagen"))
        }
    }
}