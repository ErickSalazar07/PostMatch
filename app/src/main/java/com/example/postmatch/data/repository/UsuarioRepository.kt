package com.example.postmatch.data.repository

import android.util.Log
import coil.network.HttpException
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.datasource.impl.retrofit.UsuarioRetrofitDataSourceImpl
import com.example.postmatch.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.postmatch.data.dtos.RegisterUserDto
import com.example.postmatch.data.dtos.UpdateUserDto
import com.example.postmatch.data.dtos.UsuarioDto
import com.example.postmatch.data.dtos.toReviewInfo
import com.example.postmatch.data.dtos.toUsuarioInfo
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

import kotlin.math.log

class UsuarioRepository @Inject constructor(
    // private val usuarioRemoteDataSource: UsuarioRetrofitDataSourceImpl
    private val usuarioRemoteDataSource: UserFirestoreDataSourceImpl,
    private val authRepository: AuthRepository
){

    suspend fun getUsuarioById(idUsuario: String): Result<UsuarioInfo>{

        val usuarioActual = authRepository.currentUser?.uid?: ""

        return try {
            val usuario = usuarioRemoteDataSource.getUsuarioById(idUsuario, usuarioActual)
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

    suspend fun getReviewsByUsuarioId(idUsuario: String): Result<List<ReviewInfo>>{
        return try {
            val reviews = usuarioRemoteDataSource.getReviewsByUsuarioId(idUsuario)
            val reviewsInfo = reviews.map { it.toReviewInfo() }
            Result.success(reviewsInfo)
        } catch (e: HttpException){
            e.response.code
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun registerUser(nombre: String, email: String, password: String, userId: String, fotoPerfilUrl: String): Result<Unit>{

        return try {
            val registerUserDto = RegisterUserDto(nombre, email, password, fotoPerfilUrl)
            usuarioRemoteDataSource.registerUser(registerUserDto,userId)
            Result.success(Unit)
        }catch (e: Exception){
            Log.d("TAG", "getUserById: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun updateFotoPerfilById(idUsuario: String, fotoPerfilUrl: String): Result<Unit> {
        return try {
            usuarioRemoteDataSource.updateFotoPerfilById(idUsuario, fotoPerfilUrl)
            Result.success(Unit)
        } catch(e: Exception) {
            Log.d("TAG", "updateFotoPerfilById: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun updateUser(userId: String, nombre: String, email: String, password: String): Result<Unit> {
        return try {
            val updateUserDto = UpdateUserDto(nombre, email, password)
            usuarioRemoteDataSource.updateUser(userId, updateUserDto)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("TAG", "updateUser: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getUsuarios(): Result<List<UsuarioInfo>> {
        return try {
            val usuarios = usuarioRemoteDataSource.getAllUsuarios()
            val usuariosInfo = usuarios.map { it.toUsuarioInfo() }
            Result.success(usuariosInfo)
        } catch (e: HttpException) {
            e.response.code
            Log.d("TAG", "getUsuarios: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.d("TAG", "getUsuarios: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun seguirTantoDejarDeSeguirUsuario(idUsuarioActual: String, idUsuarioSeguir: String) : Result<Unit>{
        return try{
            usuarioRemoteDataSource.seguirTantoDejarDeSeguirUsuario(idUsuarioActual = idUsuarioActual, idUsuarioSeguir = idUsuarioSeguir)
            Result.success(Unit)
        } catch(e : Exception) {
            Log.e("seguirTantoDejarDeSeguir", "Falló aquí en => seguirTantoDejarDeSeguirUsuario")
            Result.failure(e)
        }
    }
}
