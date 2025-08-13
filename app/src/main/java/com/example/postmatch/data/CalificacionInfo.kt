package com.example.postmatch.data

data class CalificacionInfo(
    var idCalificacion:Int,
    var calificacion: Int,
    var fecha: String,
// datos usuario
    var usuarioNombre: String,
    var usuarioEmail: String,
    var usuarioPassword: String,
    var usuarioFotoPerfil: String,
// datos review
    var reviewTitulo: String,
    var reviewDescripcion: String,
    var reviewFecha: String
)
