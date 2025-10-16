package com.example.postmatch.data.repository

import coil.network.HttpException
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.datasource.impl.firestore.PartidoFirestoreDataSourceImpl
import com.example.postmatch.data.dtos.toPartidoInfo
import com.example.postmatch.data.dtos.toReviewInfo
import javax.inject.Inject

class PartidoRepository @Inject constructor(
    private val partidoRemoteDataSource: PartidoFirestoreDataSourceImpl
) {
    suspend fun getPartidos(): Result<List<PartidoInfo>> {
        return try {
            val partidos = partidoRemoteDataSource.getAllPartidos()
            val partidoInfo = partidos.map { it.toPartidoInfo() }
            Result.success(partidoInfo)
        } catch (e: HttpException){
            e.response.code
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPartidoById(id:String): Result<PartidoInfo> {
        return try {
            val partido = partidoRemoteDataSource.getPartidoById(id)
            val partidoInfo = partido.toPartidoInfo()
            Result.success(partidoInfo)
        } catch (e: HttpException){
            e.response.code
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReviewsByPartidoId(idPartido: String): Result<List<ReviewInfo>> {
        return try {
            val reviews = partidoRemoteDataSource.getReviewsByPartidoId(idPartido)
            val reviewsInfo = reviews.map { it.toReviewInfo() }
            Result.success(reviewsInfo)
        } catch (e: HttpException){
            e.response.code
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}