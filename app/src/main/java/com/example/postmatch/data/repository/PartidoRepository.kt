package com.example.postmatch.data.repository

import android.util.Log
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.datasource.impl.firestore.PartidoFirestoreDataSourceImpl
import com.example.postmatch.data.dtos.toPartidoInfo
import com.example.postmatch.data.dtos.toReviewInfo
import com.google.firebase.firestore.FirebaseFirestoreException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PartidoRepository @Inject constructor(
    private val partidoRemoteDataSource: PartidoFirestoreDataSourceImpl
) {

    suspend fun getPartidos(): Result<List<PartidoInfo>> {
        return try {
            val partidos = partidoRemoteDataSource.getAllPartidos()
            val partidoInfo = partidos.map { it.toPartidoInfo() }
            Result.success(partidoInfo)
        } catch (_: HttpException) {
            Log.e("PartidoRepository", "Error HTTP al obtener los partidos.")
            Result.failure(Exception("No se pudo conectar correctamente con el servidor (error HTTP)."))
        } catch (_: FirebaseFirestoreException) {
            Log.e("PartidoRepository", "Error de Firestore al obtener los partidos.")
            Result.failure(Exception("Ocurrió un error al acceder a los datos en Firestore."))
        } catch (_: IOException) {
            Log.e("PartidoRepository", "Error de red al obtener los partidos.")
            Result.failure(Exception("Verifica tu conexión a internet e intenta nuevamente."))
        } catch (_: Exception) {
            Log.e("PartidoRepository", "Error inesperado al obtener los partidos.")
            Result.failure(Exception("Error interno al obtener la lista de partidos."))
        }
    }

    suspend fun getPartidoById(id: String): Result<PartidoInfo> {
        return try {
            val partido = partidoRemoteDataSource.getPartidoById(id)
            val partidoInfo = partido.toPartidoInfo()
            Result.success(partidoInfo)
        } catch (_: HttpException) {
            Log.e("PartidoRepository", "Error HTTP al obtener el partido con id $id.")
            Result.failure(Exception("No se pudo obtener el partido debido a un error de comunicación con el servidor."))
        } catch (_: FirebaseFirestoreException) {
            Log.e("PartidoRepository", "Error de Firestore al obtener el partido con id $id.")
            Result.failure(Exception("El partido no se encuentra disponible en la base de datos."))
        } catch (_: IOException) {
            Log.e("PartidoRepository", "Error de red al obtener el partido con id $id.")
            Result.failure(Exception("No fue posible conectar con el servidor. Revisa tu conexión."))
        } catch (_: Exception) {
            Log.e("PartidoRepository", "Error inesperado al obtener el partido con id $id.")
            Result.failure(Exception("Error interno al intentar recuperar la información del partido."))
        }
    }

    suspend fun getReviewsByPartidoId(idPartido: String): Result<List<ReviewInfo>> {
        return try {
            val reviews = partidoRemoteDataSource.getReviewsByPartidoId(idPartido)
            val reviewsInfo = reviews.map { it.toReviewInfo() }
            Result.success(reviewsInfo)
        } catch (_: HttpException) {
            Log.e("PartidoRepository", "Error HTTP al obtener reseñas del partido $idPartido.")
            Result.failure(Exception("No se pudieron cargar las reseñas debido a un problema con el servidor."))
        } catch (_: FirebaseFirestoreException) {
            Log.e("PartidoRepository", "Error de Firestore al obtener reseñas del partido $idPartido.")
            Result.failure(Exception("Ocurrió un error al acceder a las reseñas en Firestore."))
        } catch (_: IOException) {
            Log.e("PartidoRepository", "Error de red al obtener reseñas del partido $idPartido.")
            Result.failure(Exception("No fue posible conectar con el servidor. Verifica tu conexión a internet."))
        } catch (_: Exception) {
            Log.e("PartidoRepository", "Error inesperado al obtener reseñas del partido $idPartido.")
            Result.failure(Exception("Error interno al obtener la lista de reseñas."))
        }
    }
}
