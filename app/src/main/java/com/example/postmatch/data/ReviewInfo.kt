package com.example.postmatch.data

data class ReviewInfo(
    var idReview: Int = 0,
    var titulo: String = "",
    var descripcion: String = "",
    var fecha: String = "",
    var calificacion: Int = 0,
    var numLikes: Int = 0,
    var numComentarios: Int = 0,
// datos usuario
    var usuarioNombre: String = "",
    var usuarioEmail: String = "",
    var usuarioPassword: String = "",
    var usuarioFotoPerfil: String = "",
// datos partido
    var partidoNombre: String = "",
    var partidoCategoria: String = "",
    var partidoVisitante: String = "",
    var partidoLocal: String = "",
    var partidoGolesVisitante: Int = 0,
    var partidoGolesLocal: Int = 0,
    var partidoFecha: String = ""
)
