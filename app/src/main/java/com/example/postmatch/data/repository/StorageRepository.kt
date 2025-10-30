package com.example.postmatch.data.repository

import android.net.Uri
import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.example.postmatch.data.datasource.StorageRemoteDataSource
import com.example.postmatch.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.StorageException
import java.io.IOException
import javax.inject.Inject

class StorageRepository @Inject constructor(
    private val storage: StorageRemoteDataSource,
    private val usuarioDataSource: UserFirestoreDataSourceImpl,
    private val auth: AuthRemoteDataSource
) {
    suspend fun uploadProfileImage(uri: Uri): Result<String> {
        return try {
            val userId = auth.currentUser?.uid
                ?: return Result.failure(Exception("Usuario no autenticado. Inicia sesión para continuar."))

            val path = "profileImages/$userId.jpg"
            val url = storage.uploadImage(path, uri)

            auth.updateProfileImage(url)
            usuarioDataSource.updateFotoPerfilById(userId, url)

            Result.success(url)
        } catch (_: FirebaseNetworkException) {
            Result.failure(Exception("Error de conexión con Firebase. Verifica tu red e inténtalo de nuevo."))
        } catch (_: StorageException) {
            Result.failure(Exception("No se pudo subir la imagen al almacenamiento. Inténtalo más tarde."))
        } catch (_: FirebaseFirestoreException) {
            Result.failure(Exception("Error al actualizar la imagen en la base de datos del usuario."))
        } catch (_: FirebaseAuthException) {
            Result.failure(Exception("Error de autenticación al intentar actualizar el perfil."))
        } catch (_: IOException) {
            Result.failure(Exception("Fallo de red. No se pudo completar la subida de la imagen."))
        } catch (_: Exception) {
            Result.failure(Exception("Error inesperado al subir la imagen de perfil."))
        }
    }
}
