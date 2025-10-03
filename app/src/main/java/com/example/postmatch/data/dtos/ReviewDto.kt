package com.example.postmatch.data.dtos

import com.example.postmatch.data.ReviewInfo
import java.util.Date

data class ReviewDto(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val fecha: Date,
    val idUsuario: Int,
    val idPartido: Int,
)

fun ReviewDto.toReviewInfo(): ReviewInfo {
    return ReviewInfo(
        idReview = id.toString(),
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha.toString(),
    )
}

