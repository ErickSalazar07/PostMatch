package com.example.postmatch.data.repository

import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepository @Inject constructor (
    private val authRemoteDataSource: AuthRemoteDataSource,
) {

    val currentUser: FirebaseUser? = authRemoteDataSource.currentUser

    suspend fun singIn(email: String, password: String) {
        authRemoteDataSource.singIn(email, password)
    }

    suspend fun signUp(email: String, password: String) {
        authRemoteDataSource.signUp(email, password)
    }

    fun signOut() {
        authRemoteDataSource.signOut()
    }

}