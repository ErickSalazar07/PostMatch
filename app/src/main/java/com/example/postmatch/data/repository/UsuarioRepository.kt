package com.example.postmatch.data.repository

import android.util.Log
import coil.network.HttpException
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.example.postmatch.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.postmatch.data.dtos.RegisterUserDto
import com.example.postmatch.data.dtos.UpdateUserDto
import com.example.postmatch.data.dtos.toReviewInfo
import com.example.postmatch.data.dtos.toUsuarioInfo
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import javax.inject.Inject

class UsuarioRepository @Inject constructor(
    private val usuarioRemoteDataSource: UserFirestoreDataSourceImpl,
    private val authRepository: AuthRemoteDataSource
) {

    suspend fun getUsuarioById(idUsuario: String): Result<UsuarioInfo> {
        val usuarioActual = authRepository.currentUser?.uid ?: "noid"

        return try {
            val usuario = usuarioRemoteDataSource.getUsuarioById(idUsuario, usuarioActual)
            Result.success(usuario.toUsuarioInfo())
        } catch (_: FirebaseFirestoreException) {
            Log.e("UsuarioRepository", "Error al obtener el usuario desde Firestore")
            Result.failure(Exception("No se pudo obtener la información del usuario desde la base de datos"))
        } catch (_: FirebaseNetworkException) {
            Log.e("UsuarioRepository", "Error de red al obtener el usuario")
            Result.failure(Exception("Error de conexión. Verifica tu red e inténtalo de nuevo"))
        } catch (_: HttpException) {
            Log.e("UsuarioRepository", "Error de red al obtener el usuario")
            Result.failure(Exception("Ocurrió un error al comunicarse con el servidor"))
        } catch (_: Exception) {
            Log.e("UsuarioRepository", "Error inesperado al obtener el usuario")
            Result.failure(Exception("Ocurrió un error inesperado al obtener la información del usuario"))
        }
    }

    suspend fun getReviewsByUsuarioId(idUsuario: String): Result<List<ReviewInfo>> {
        return try {
            val reviews = usuarioRemoteDataSource.getReviewsByUsuarioId(idUsuario)
            Result.success(reviews.map { it.toReviewInfo() })
        } catch (_: FirebaseFirestoreException) {
            Result.failure(Exception("No se pudieron cargar las reseñas del usuario"))
        } catch (_: FirebaseNetworkException) {
            Result.failure(Exception("Error de conexión al obtener las reseñas, revisa tu internet"))
        } catch (_: HttpException) {
            Result.failure(Exception("Ocurrió un error al comunicarse con el servidor"))
        } catch (_: Exception) {
            Result.failure(Exception("Ocurrió un error inesperado al obtener las reseñas"))
        }
    }

    suspend fun registerUser(nombre: String, email: String, password: String, userId: String, fotoPerfilUrl: String): Result<Unit> {
        return try {
            val registerUserDto = RegisterUserDto(nombre, email, password, fotoPerfilUrl)
            usuarioRemoteDataSource.registerUser(registerUserDto, userId)
            Result.success(Unit)
        } catch (_: FirebaseAuthException) {
            Result.failure(Exception("No se pudo registrar el usuario, revisa los datos ingresados"))
        } catch (_: FirebaseFirestoreException) {
            Result.failure(Exception("No se pudo guardar el usuario en la base de datos"))
        } catch (_: FirebaseNetworkException) {
            Result.failure(Exception("Error de conexión al registrar el usuario"))
        } catch (_: Exception) {
            Result.failure(Exception("Ocurrió un error inesperado al registrar el usuario"))
        }
    }

    suspend fun updateFotoPerfilById(idUsuario: String, fotoPerfilUrl: String): Result<Unit> {
        return try {
            usuarioRemoteDataSource.updateFotoPerfilById(idUsuario, fotoPerfilUrl)
            Result.success(Unit)
        } catch (_: FirebaseFirestoreException) {
            Result.failure(Exception("No se pudo actualizar la foto de perfil"))
        } catch (_: FirebaseNetworkException) {
            Result.failure(Exception("Error de conexión al actualizar la foto de perfil"))
        } catch (_: Exception) {
            Result.failure(Exception("Ocurrió un error inesperado al actualizar la foto de perfil"))
        }
    }

    suspend fun updateUser(userId: String, nombre: String, email: String, password: String): Result<Unit> {
        return try {
            val updateUserDto = UpdateUserDto(nombre, email, password)
            usuarioRemoteDataSource.updateUser(userId, updateUserDto)
            Result.success(Unit)
        } catch (_: FirebaseFirestoreException) {
            Result.failure(Exception("No se pudo actualizar la información del usuario"))
        } catch (_: FirebaseNetworkException) {
            Result.failure(Exception("Error de conexión al actualizar los datos del usuario"))
        } catch (_: Exception) {
            Result.failure(Exception("Ocurrió un error inesperado al actualizar el usuario"))
        }
    }

    suspend fun getUsuarios(): Result<List<UsuarioInfo>> {
        return try {
            val usuarios = usuarioRemoteDataSource.getAllUsuarios()
            Result.success(usuarios.map { it.toUsuarioInfo() })
        } catch (_: FirebaseFirestoreException) {
            Result.failure(Exception("No se pudieron obtener los usuarios desde la base de datos"))
        } catch (_: FirebaseNetworkException) {
            Result.failure(Exception("Error de conexión al obtener la lista de usuarios"))
        } catch (_: HttpException) {
            Result.failure(Exception("Ocurrió un error al comunicarse con el servidor"))
        } catch (_: Exception) {
            Result.failure(Exception("Ocurrió un error inesperado al obtener los usuarios"))
        }
    }

    suspend fun seguirTantoDejarDeSeguirUsuario(idUsuarioActual: String, idUsuarioSeguir: String): Result<Unit> {
        return try {
            usuarioRemoteDataSource.seguirTantoDejarDeSeguirUsuario(idUsuarioActual, idUsuarioSeguir)
            Result.success(Unit)
        } catch (_: FirebaseFirestoreException) {
            Result.failure(Exception("No se pudo realizar la acción de seguir o dejar de seguir"))
        } catch (_: FirebaseNetworkException) {
            Result.failure(Exception("Error de conexión al intentar seguir o dejar de seguir al usuario"))
        } catch (_: Exception) {
            Result.failure(Exception("Ocurrió un error inesperado al realizar la acción"))
        }
    }
}
