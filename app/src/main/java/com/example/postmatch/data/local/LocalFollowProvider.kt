package com.example.postmatch.data.local

import com.example.postmatch.data.FollowInfo

object LocalFollowProvider {
    var follows = listOf(
        FollowInfo(
            idFollow = 1,
            fecha = "10/08/2025",
            usuarioFollowerNombre = "Pedro",
            usuarioFollowerEmail = "p@noemail",
            usuarioFollowerPassword = "54321",
            usuarioFollowerFotoPerfil = "usuario1.png",
            usuarioFollowedNombre = "Juan",
            usuarioFollowedEmail = "j@noemail",
            usuarioFollowedPassword = "12345",
            usuarioFollowedFotoPerfil = "usuario1.png",
        )
    )
}