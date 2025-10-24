package com.example.postmatch.data.datasource

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor (
    private val auth: FirebaseAuth
) {

    //val currentUser: FirebaseUser? = auth.currentUser


    val currentUser: FirebaseUser?
        get() = auth.currentUser

    suspend fun singIn(email: String, password: String): Unit {
        auth.signInWithEmailAndPassword(email,password).await()
    }

    suspend fun signUp(email: String, password: String): Unit {
        auth.createUserWithEmailAndPassword(email,password).await()
    }

    fun signOut() {
        auth.signOut()
    }

    suspend fun updateProfileImage(perfilImageUrl: String): Unit {
        val uri = Uri.parse(perfilImageUrl)
        currentUser?.updateProfile(
        UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build()
        )?.await()
    }

    suspend fun fetchUserByEmail(email: String): Boolean {
        return try {
            val result = auth.fetchSignInMethodsForEmail(email).await()
            result.signInMethods?.isNotEmpty() == true
        } catch (e: Exception) {
            false
        }
    }

}