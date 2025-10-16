package com.example.postmatch.data.dtos

import com.example.postmatch.data.UsuarioInfo

data class UsuarioDto(
    val id: String,
    val nombre: String,
    val email: String,
    val password: String,
    val fotoPerfilUrl: String
){
    constructor():this("0","","","","")
}

fun UsuarioDto.toUsuarioInfo(): UsuarioInfo {
    return UsuarioInfo(
        idUsuario = id,
        nombre = nombre,
        email = email,
        password = password,
        fotoPerfil = fotoPerfilUrl
    )
}
