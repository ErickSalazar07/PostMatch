package com.example.postmatch.data.dto

data class ReviewDTO(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val idUsuario: Int,
    val idPartido: Int,
    val createdAt: String,
    val updatedAt: String
)
