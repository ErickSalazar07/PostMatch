package com.example.postmatch.data.repository

import coil.network.HttpException
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.datasource.impl.PartidoRetrofitDataSourceImpl
import com.example.postmatch.data.dtos.toPartidoInfo
import javax.inject.Inject

class PartidoRepository @Inject constructor(
    private val partidoRemoteDataSource: PartidoRetrofitDataSourceImpl
) {
    suspend fun getPartidos(): Result<List<PartidoInfo>> {
        return try {
            val partidos = partidoRemoteDataSource.getAllPartidos()
            val partidoInfo = partidos.map { it.toPartidoInfo() }
            Result.success(partidoInfo)

        } catch (e: HttpException){
            e.response.code
            Result.failure(e)
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPartidoById(id:Int): Result<PartidoInfo> {
        return try {
            val partido = partidoRemoteDataSource.getPartidoById(id.toString())
            val partidoInfo = partido.toPartidoInfo()
            Result.success(partidoInfo)
        } catch (e: HttpException){
            e.response.code
            Result.failure(e)
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}