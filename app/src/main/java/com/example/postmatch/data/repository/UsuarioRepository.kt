package com.example.postmatch.data.repository

import android.util.Log
import coil.network.HttpException
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.datasource.impl.UsuarioRetrofitDataSourceImpl
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
      private val usuarioRemoteDataSource: UserFirestoreDataSourceImpl
){


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


    suspend fun registerUser(nombre: String, email: String, password: String, userId: String): Result<Unit>{

        return try {
            val registerUserDto = RegisterUserDto(nombre, email, password)
            usuarioRemoteDataSource.registerUser(registerUserDto,userId)
            Result.success(Unit)
        }catch (e: Exception){
            Log.d("TAG", "getUserById: ${e.message}")
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


    /*
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


     */

    suspend fun getUsuarios(): Result<List<UsuarioInfo>> {
        return try {
            val usuarios = usuarioRemoteDataSource.getAllUsuarios()
            val usuariosInfo = usuarios.map { it.toUsuarioInfo() }
            Result.success(usuariosInfo)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



}



