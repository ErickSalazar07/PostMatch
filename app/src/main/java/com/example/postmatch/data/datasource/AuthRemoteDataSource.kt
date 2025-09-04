package com.example.postmatch.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor (
    private val auth: FirebaseAuth
) {

    val currentUser: FirebaseUser? = auth.currentUser

    suspend fun singIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).await()
    }

    suspend fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).await()
    }

    fun signOut() {
        auth.signOut()
    }
}