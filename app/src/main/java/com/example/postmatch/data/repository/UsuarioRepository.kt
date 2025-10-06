package com.example.postmatch.data.repository

import coil.network.HttpException
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.datasource.impl.UsuarioRetrofitDataSourceImpl
import com.example.postmatch.data.dtos.UsuarioDto
import com.example.postmatch.data.dtos.toReviewInfo
import com.example.postmatch.data.dtos.toUsuarioInfo
import javax.inject.Inject

class UsuarioRepository @Inject constructor(
    private val usuarioRemoteDataSource: UsuarioRetrofitDataSourceImpl
){
    suspend fun getUsuarios(): Result<List<UsuarioInfo>>{
        return try {
            val usuarios = usuarioRemoteDataSource.getAllUsuarios()
            val usuariosInfo = usuarios.map { it.toUsuarioInfo() }
            Result.success(usuariosInfo)

        } catch (e: HttpException){
            e.response.code
            Result.failure(e)
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUsuarioById(idUsuario: Int): Result<UsuarioInfo>{
        return try {
            val usuario = usuarioRemoteDataSource.getUsuarioById(idUsuario.toString())
            val usuarioInfo = usuario.toUsuarioInfo()
            Result.success(usuarioInfo)

        } catch (e: HttpException){
            e.response.code
            Result.failure(e)
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReviewsByUsuarioId(idUsuario: Int): Result<List<ReviewInfo>>{
        return try {
            val reviews = usuarioRemoteDataSource.getReviewsByUsuarioId(idUsuario.toString())
            val reviewsInfo = reviews.map { it.toReviewInfo() }
            Result.success(reviewsInfo)
        } catch (e: HttpException){
            e.response.code
            Result.failure(e)
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}

