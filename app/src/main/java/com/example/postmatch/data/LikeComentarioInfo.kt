package com.example.postmatch.data

data class LikeComentarioInfo(
    var idLikeComentario: Int,
    var fecha: String,
// datos comentario
    var comentarioDescripcion: String,
    var comentarioFecha: String,
// datos usuario
    var usuarioNombre: String,
    var usuarioEmail: String,
    var usuarioPassword: String,
    var usuarioFotoPerfil: String,
)
