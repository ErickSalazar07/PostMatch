package com.example.postmatch.data.dtos

import java.util.Date

data class CreateReviewDto (
    var titulo: String = "",
    var descripcion: String = "",
    var fecha: Date = Date(),
    var calificacion: Int = 1,
    var idUsuario: String = "",
    var idPartido: String = "",
    var partido: PartidoCreateDto? = null,
    var usuario: UsuarioCreateDto? = null
)

data class PartidoCreateDto(
    val fotoUrl: String? = null
)

data class UsuarioCreateDto(
    val nombre: String? = null,
    val email: String? = null,
    val fotoPerfilUrl: String? = null
)
