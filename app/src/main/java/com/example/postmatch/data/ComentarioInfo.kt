package com.example.postmatch.data

data class ComentarioInfo(
    var idComentario: Int,
    var descripcion: String,
    var fecha: String,
// datos usuario
    var usuarioNombre: String,
    var usuarioEmail: String,
    var usuarioPassword: String,
    var usuarioFotoPerfil: String,
    var idUsuarioFotoPerfil: Int,
// datos review
    var reviewTitulo: String,
    var reviewDescripcion: String,
    var reviewFecha: String
)
