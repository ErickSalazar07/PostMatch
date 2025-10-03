package com.example.postmatch.data.dto

import com.example.postmatch.data.ReviewInfo

data class ReviewDTO(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val calificacion: Int,
    val numLikes: Int,
    val numComentarios: Int,
    val idUsuario: Int,
    val idPartido: Int,
    val createdAt: String,
    val updatedAt: String
)

fun ReviewDTO.toReviewInfo(): ReviewInfo {
    return ReviewInfo(
        idReview = id.toString(),
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha,
        calificacion = calificacion,
        numLikes = numLikes,
        numComentarios = numComentarios
    )
}
