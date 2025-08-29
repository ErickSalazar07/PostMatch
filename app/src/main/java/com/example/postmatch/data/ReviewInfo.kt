package com.example.postmatch.data

data class ReviewInfo(
    var idReview: Int,
    var titulo: String,
    var descripcion: String,
    var fecha: String,
    var calificacion: Int,
    var numLikes: Int,
    var numComentarios: Int,
// datos usuario
    var usuarioNombre: String,
    var usuarioEmail: String,
    var usuarioPassword: String,
    var usuarioFotoPerfil: String,
// datos partido
    var partidoNombre: String,
    var partidoCategoria: String,
    var partidoVisitante: String,
    var partidoLocal: String,
    var partidoGolesVisitante: Int,
    var partidoGolesLocal: Int,
    var partidoFecha: String
)
