package com.example.postmatch.data.dto

data class UsuarioDTO(
    val id: Int,
    val nombre: String,
    val email: String,
    val password: String,
    val fotoPerfilUrl: String,
    val createdAt: String,
    val updatedAt: String
)
