package com.example.postmatch.data.dtos

import com.example.postmatch.data.UsuarioInfo

data class UsuarioDto(
    val id: String,
    val nombre: String,
    val email: String,
    val password: String,
    val fotoPerfilUrl: String,
    var followed : Boolean,
    var numFollowed: Int,
    var numFollowers: Int

){
    constructor():this("0", "", "", "", "", false, 0, 0)
}

fun UsuarioDto.toUsuarioInfo(): UsuarioInfo {
    return UsuarioInfo(
        idUsuario = id,
        nombre = nombre,
        email = email,
        password = password,
        fotoPerfil = fotoPerfilUrl,
        followed = followed,
        numFollowers = numFollowers,
        numFollowed = numFollowed
    )
}
