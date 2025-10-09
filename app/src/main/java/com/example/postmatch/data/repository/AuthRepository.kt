package com.example.postmatch.data.repository

import android.util.Log
import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.example.postmatch.data.injection.FirebaseHiltModule_AuthFactory.auth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthWebException
import com.google.firebase.auth.FirebaseUser
import java.lang.System.console
import javax.inject.Inject

class AuthRepository @Inject constructor (
    private val authRemoteDataSource: AuthRemoteDataSource,
) {

    //val currentUser: FirebaseUser? = authRemoteDataSource.currentUser
    val currentUser: FirebaseUser?
        get() = authRemoteDataSource.currentUser

    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {

            if(email.isNullOrEmpty() || password.isNullOrEmpty()) throw Exception("Todos los campos son obligatorios")

            authRemoteDataSource.singIn(email, password)
            Result.success(Unit)
        } catch(e: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("Credenciales incorrectas"))
        } catch(e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Correo inválido"))
        } catch(e: FirebaseAuthException){
            Result.failure(Exception("Error al inciar sesión"))
        } catch(e: Exception) {
            Result.failure(Exception(e.message ?: "Error desconocido"))
        }
    }

    suspend fun signUp(email: String, password: String): Result<Unit> {

        return try {

            if(email.isNullOrEmpty() || password.isNullOrEmpty()) throw Exception("Todos los campos son obligatorios")

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
