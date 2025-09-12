package com.example.postmatch.data.local

import com.example.postmatch.data.UsuarioInfo

object LocalUsuarioProvider {
    var usuarios = listOf(
        UsuarioInfo(
            idUsuario = 1,
            nombre = "Pedro",
            email = "p@noemail",
            password = "54321",
            fotoPerfil = "https://firebasestorage.googleapis.com/v0/b/postmatch-c2ddd.firebasestorage.app/o/imageUtils%2Fusuario_foto_perfil_01.jpg?alt=media&token=f8b0fe4f-86bd-4341-ba0b-0e0b10da1ea60",
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