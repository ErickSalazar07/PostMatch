package com.example.postmatch.data.dtos

import com.example.postmatch.data.ReviewInfo
import java.util.Date

data class ReviewDto(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val fecha: Date,
    val calificacion: Int,
    val numLikes: Int?,
    val numComentarios: Int?,
    val idUsuario: String,
    val idPartido: String,
    val partido: PartidoCreateDto? = null,
    val usuario: UsuarioCreateDto? = null,
    val likedByUser: Boolean = false
) {
    constructor(): this("","","",Date(),0,0,0,"","")
}

fun ReviewDto.toReviewInfo(): ReviewInfo {
    return ReviewInfo(
        idReview = id,
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha.toString(),
        calificacion = calificacion,
        numLikes = numLikes?: 0,
        numComentarios = numComentarios?: 0,
        usuarioId = idUsuario,
        partidoId = idPartido,
        partidoFotoUrl = partido?.fotoUrl ?: "",
        usuarioNombre = usuario?.nombre ?: "",
        usuarioEmail = usuario?.email ?: "",
        usuarioFotoPerfil = usuario?.fotoPerfilUrl ?: "",
        likedByUser = likedByUser,
    )
}
