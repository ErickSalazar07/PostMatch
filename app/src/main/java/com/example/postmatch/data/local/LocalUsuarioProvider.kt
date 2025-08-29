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
            numFollowed = 10,
            numFollowers = 5
        ),
        UsuarioInfo(
            idUsuario = 2,
            nombre = "Juan",
            email = "j@noemail",
            password = "12345",
            fotoPerfil = "usuario1.png",
            numFollowed = 20,
            numFollowers = 15
        ),
        UsuarioInfo(
            idUsuario = 3,
            nombre = "Ana",
            email = "a@noemail",
            password = "password123",
            fotoPerfil = "usuario2.png",
            numFollowed = 5,
            numFollowers = 25
        ),
        UsuarioInfo(
            idUsuario = 4,
            nombre = "Maria",
            email = "m@noemail",
            password = "securepassword",
            fotoPerfil = "usuario3.png",
            numFollowed = 30,
            numFollowers = 50
        )
    )
}