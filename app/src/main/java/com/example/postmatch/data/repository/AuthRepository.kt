package com.example.postmatch.data.repository

import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepository @Inject constructor (
    private val authRemoteDataSource: AuthRemoteDataSource,
) {

    val currentUser: FirebaseUser? = authRemoteDataSource.currentUser

    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            authRemoteDataSource.singIn(email, password)
            Result.success(Unit)
        } catch(e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Credenciales incorrectas"))
        } catch(e: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("Usuario invalido"))
        } catch(e: Exception) {
            Result.failure(Exception("Error iniciar sesion"))
        }
    }

    suspend fun signUp(email: String, password: String): Result<Unit> {
        return try {
            authRemoteDataSource.signUp(email, password)
            Result.success(Unit)
        } catch(e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Credenciales incorrectas"))
        } catch(e: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("Usuario invalido"))
        } catch(e: Exception) {
            Result.failure(Exception("Error al crear usuario"))
        }
    }

    fun signOut() {
        authRemoteDataSource.signOut()
    }

}