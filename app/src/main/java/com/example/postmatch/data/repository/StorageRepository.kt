package com.example.postmatch.data.repository

import android.net.Uri
import android.util.Log
import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.example.postmatch.data.datasource.StorageRemoteDataSource
import com.example.postmatch.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import javax.inject.Inject

class StorageRepository @Inject constructor(
    private val storage: StorageRemoteDataSource,
    private val usuarioDataSource: UserFirestoreDataSourceImpl,
    private val auth: AuthRemoteDataSource
) {
    suspend fun uploadProfileImage(uri: Uri): Result<String> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Usuario no autenticado"))
            val path = "profileImages/$userId.jpg"
            val url = storage.uploadImage(path, uri)
            auth.updateProfileImage(url) // actualiza url del usuario
            usuarioDataSource.updateFotoPerfilById(userId, url)
            Result.success(url)
        } catch(e: Exception) {
            Log.e("StorageRepository", "Error al subir la imagen ${e.message}")
            Result.failure(Exception("Error al subir la imagen"))
        }
    }
}