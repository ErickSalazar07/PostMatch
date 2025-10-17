package com.example.postmatch.data.datasource.impl.retrofit

import com.example.postmatch.data.datasource.UsuarioRemoteDataSource
import com.example.postmatch.data.datasource.services.UsuarioRetrofitService
import com.example.postmatch.data.dtos.RegisterUserDto
import com.example.postmatch.data.dtos.ReviewDto
import com.example.postmatch.data.dtos.UpdateUserDto
import com.example.postmatch.data.dtos.UsuarioDto
import javax.inject.Inject

class UsuarioRetrofitDataSourceImpl @Inject constructor(
    val service: UsuarioRetrofitService
): UsuarioRemoteDataSource {
    override suspend fun getAllUsuarios(): List<UsuarioDto> {
        return service.getAllUsuarios()
    }

    override suspend fun getUsuarioById(id: String, idUsuarioActual: String): UsuarioDto {
        return service.getUsuarioById(id.toInt())
    }

    override suspend fun getReviewsByUsuarioId(idUsuario: String): List<ReviewDto> {
        return service.getReviewsByUsuarioId(idUsuario = idUsuario.toInt())
    }

    override suspend fun registerUser(registerUserDto: RegisterUserDto, userId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(userId: String, updateUserDto: UpdateUserDto) {
        TODO("Not yet implemented")
    }

    override suspend fun seguirTantoDejarDeSeguirUsuario(idUsuarioActual: String, idUsuarioSeguir: String){
        TODO("not implemented yet")
    }
}