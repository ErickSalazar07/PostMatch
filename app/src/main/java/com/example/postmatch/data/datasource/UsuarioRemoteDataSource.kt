package com.example.postmatch.data.datasource

import com.example.postmatch.data.dtos.RegisterUserDto
import com.example.postmatch.data.dtos.ReviewDto
import com.example.postmatch.data.dtos.UpdateUserDto
import com.example.postmatch.data.dtos.UsuarioDto

interface UsuarioRemoteDataSource {
    suspend fun getAllUsuarios(): List<UsuarioDto>
    suspend fun getUsuarioById(id: String, idUsuarioActual: String): UsuarioDto
    suspend fun getReviewsByUsuarioId(idUsuario: String): List<ReviewDto>

    suspend fun registerUser(registerUserDto: RegisterUserDto, userId: String): Unit

    suspend fun updateUser(userId: String, updateUserDto: UpdateUserDto)

    suspend fun  seguirTantoDejarDeSeguirUsuario(idUsuarioActual: String, idUsuarioSeguir: String): Unit

    suspend fun getFollowersOfUserById(idUsuario: String): List<UsuarioDto>
}