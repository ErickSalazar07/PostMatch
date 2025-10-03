package com.example.postmatch.data.local

import com.example.postmatch.data.UsuarioInfo

object LocalUsuarioProvider {
    var usuarios = listOf(
        UsuarioInfo(
            idUsuario = "1",
            nombre = "Pedro",
            email = "p@noemail",
            password = "54321",
            fotoPerfil = "https://firebasestorage.googleapis.com/v0/b/postmatch-c2ddd.firebasestorage.app/o/imageUtils%2Fusuario_foto_perfil_01.jpg?alt=media&token=f8b0fe4f-86bd-4341-ba0b-0e0b10da1ea60",
            numFollowed = 10,
            numFollowers = 5
        ),
        UsuarioInfo(
            idUsuario = "2",
            nombre = "Juan",
            email = "j@noemail",
            password = "12345",
            fotoPerfil = "https://firebasestorage.googleapis.com/v0/b/postmatch-c2ddd.firebasestorage.app/o/profileImages%2Fyawarcru_pp.jpg?alt=media&token=5d689aa9-b34d-4d64-b2b4-d6ffa2aa4f91",
            numFollowed = 20,
            numFollowers = 15
        ),
        UsuarioInfo(
            idUsuario = "3",
            nombre = "Ana",
            email = "a@noemail",
            password = "password123",
            fotoPerfil = "https://firebasestorage.googleapis.com/v0/b/postmatch-c2ddd.firebasestorage.app/o/profileImages%2Flambo_pp.jpg?alt=media&token=83666719-dee3-403f-bdb2-d5f316e4a065",
            numFollowed = 5,
            numFollowers = 25
        ),
        UsuarioInfo(
            idUsuario = "4",
            nombre = "Maria",
            email = "m@noemail",
            password = "securepassword",
            fotoPerfil = "https://firebasestorage.googleapis.com/v0/b/postmatch-c2ddd.firebasestorage.app/o/profileImages%2FminecraftSteve_pp.jpg?alt=media&token=143cd863-f869-4f7a-844f-f629b862403b",
            numFollowed = 30,
            numFollowers = 50
        )
    )
}