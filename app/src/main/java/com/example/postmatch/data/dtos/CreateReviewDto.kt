package com.example.postmatch.data.dtos

import java.util.Date

data class CreateReviewDto (
    val titulo: String,
    val descripcion: String,
    val fecha: Date,
    val idUsuario: Int,
    val idPartido: Int
)