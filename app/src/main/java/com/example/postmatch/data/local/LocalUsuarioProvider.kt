package com.example.postmatch.data.local

import com.example.postmatch.data.UsuarioInfo

object LocalUsuarioProvider {
    var usuarios = listOf(
        UsuarioInfo(
        idUsuario = 1,
        nombre = "Pedro",
        email = "p@noemail",
        password = "54321",
        fotoPerfil = "1.png",
        ),
        UsuarioInfo(
            idUsuario = 2,
            nombre = "Juan",
            email = "j@noemail",
            password = "12345",
            fotoPerfil = "usuario1.png",
        )
    )
}