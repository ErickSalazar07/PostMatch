package com.example.postmatch.data.dto

data class PartidoDTO(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val visitante: String,
    val local: String,
    val golesLocal: Int,
    val golesVisitante: Int,
    val posesionLocal: Int,
    val posesionVisitante: Int,
    val tirosLocal: Int,
    val tirosVisitante: Int,
    val fecha: String,
    val createdAt: String,
    val updatedAt: String
)
