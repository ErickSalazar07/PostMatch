package com.example.postmatch.data.repository

import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
) {

    val currentUser: FirebaseUser?
        get() = authRemoteDataSource.currentUser

    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            if (email.isEmpty() || password.isEmpty())
                throw Exception("Todos los campos son obligatorios")

            authRemoteDataSource.singIn(email, password)
            Result.success(Unit)
        } catch (_: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("Credenciales incorrectas"))
        } catch (_: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Correo inválido"))
        } catch (_: FirebaseAuthException) {
            Result.failure(Exception("Error al iniciar sesión"))
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "Error desconocido"))
        }
    }

    suspend fun signUp(email: String, password: String): Result<Unit> {
        return try {
            if (email.isEmpty() || password.isEmpty())
                throw Exception("Todos los campos son obligatorios")

            authRemoteDataSource.signUp(email, password)
            Result.success(Unit)
        } catch (_: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("El correo electrónico es inválido"))
        } catch (_: FirebaseAuthUserCollisionException) {
            Result.failure(Exception("Este correo ya está en uso"))
        } catch (e: FirebaseAuthException) {
            // Detecta si el error fue por correo duplicado aunque no venga como UserCollisionException
            if (e.errorCode == "ERROR_EMAIL_ALREADY_IN_USE") {
                Result.failure(Exception("Este correo ya está registrado"))
            } else {
                Result.failure(Exception("Error al registrar el usuario, intente de nuevo más tarde."))
            }
        } catch (_: FirebaseAuthWeakPasswordException) {
            Result.failure(Exception("Contraseña demasiado débil"))
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "Error desconocido"))
        }
    }

    fun signOut() {
        authRemoteDataSource.signOut()
    }
}
