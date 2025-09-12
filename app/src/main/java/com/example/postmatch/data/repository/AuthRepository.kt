package com.example.postmatch.data.repository

import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthWebException
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
            Result.failure(Exception("Correo inválido"))
        } catch(e: FirebaseAuthException){
            Result.failure(Exception("Error al inciar sesión"))
        } catch(e: Exception) {
           Result.failure(Exception("Error de la Autorización"))
        }
    }

    suspend fun signUp(email: String, password: String): Result<Unit> {
        return try {
            authRemoteDataSource.signUp(email, password)
            Result.success(Unit)
        } catch(e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("El correo electrónico es inválido"))
        } catch(e: FirebaseAuthUserCollisionException) {
            Result.failure(Exception("Este correo ya está en uso"))
        } catch(e: FirebaseAuthWeakPasswordException){
            Result.failure(Exception("Contraseña demasiado débil"))
        }catch(e: FirebaseAuthException){
            Result.failure(Exception("Error al registrar el usuario intente de nuevo más tarde."))
        } catch(e: Exception){
            Result.failure(Exception("Error de la Autorización"))
        }
    }

    fun signOut() {
        authRemoteDataSource.signOut()
    }

     fun isLoggedIn(): Boolean {
        return currentUser != null;
    }

}
