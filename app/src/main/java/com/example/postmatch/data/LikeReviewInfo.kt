package com.example.postmatch.data

data class LikeReviewInfo(
    var idLikeReview: Int,
    var fecha: String,
// datos review
    var reviewTitulo: String,
    var reviewDescripcion: String,
    var reviewFecha: String,
// datos usuario
    var usuarioNombre: String,
    var usuarioEmail: String,
    var usuarioPassword: String,
    var usuarioFotoPerfil: String,
)
