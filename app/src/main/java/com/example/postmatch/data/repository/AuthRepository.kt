package com.example.postmatch.data.repository

import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.google.firebase.auth.*
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
) {

    val currentUser: FirebaseUser?
        get() = authRemoteDataSource.currentUser

    // -------- LOGIN ---------
    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                throw Exception("Todos los campos son obligatorios")
            }

            authRemoteDataSource.singIn(email, password)
            Result.success(Unit)

        } catch (e: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("El usuario no existe o ha sido deshabilitado"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Correo o contraseña incorrectos"))
        } catch (e: FirebaseAuthWebException) {
            Result.failure(Exception("Error de conexión con el servidor de autenticación"))
        } catch (e: FirebaseAuthException) {
            Result.failure(Exception("Error al iniciar sesión. Intente de nuevo más tarde"))
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "Error desconocido"))
        }
    }

    // -------- REGISTRO ---------
    suspend fun signUp(email: String, password: String): Result<Unit> {
        return try {
            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                throw Exception("Todos los campos son obligatorios")
            }

            // 🔍 Verifica si el usuario ya existe antes de registrarlo
            val existingUser = authRemoteDataSource.fetchUserByEmail(email)
            if (existingUser != null) {
                throw FirebaseAuthUserCollisionException("EMAIL_EXISTS", "El usuario ya está registrado")
            }

            authRemoteDataSource.signUp(email, password)
            Result.success(Unit)

        } catch (e: FirebaseAuthUserCollisionException) {
            Result.failure(Exception("Este correo ya está en uso"))
        } catch (e: FirebaseAuthWeakPasswordException) {
            Result.failure(Exception("La contraseña es demasiado débil"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("El correo electrónico no es válido"))
        } catch (e: FirebaseAuthWebException) {
            Result.failure(Exception("Error de conexión con el servidor de autenticación"))
        } catch (e: FirebaseAuthException) {
            Result.failure(Exception("Error al registrar el usuario. Intente de nuevo más tarde"))
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "Error desconocido al registrar el usuario"))
        }
    }

    fun signOut() {
        authRemoteDataSource.signOut()
    }

    fun isLoggedIn(): Boolean = currentUser != null
}
