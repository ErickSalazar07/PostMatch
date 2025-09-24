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
            usuarioFollowerFotoPerfil = "https://firebasestorage.googleapis.com/v0/b/postmatch-c2ddd.firebasestorage.app/o/profileImages%2Fsan_pp.jpg?alt=media&token=ac786d04-b728-4d57-9b99-a975cb4a2ae8",
            usuarioFollowedNombre = "Juan",
            usuarioFollowedEmail = "j@noemail",
            usuarioFollowedPassword = "12345",
            usuarioFollowedFotoPerfil = "https://firebasestorage.googleapis.com/v0/b/postmatch-c2ddd.firebasestorage.app/o/profileImages%2Fsurprised_pp.jpg?alt=media&token=ef3ffa1a-a552-4f6a-a151-97a02bbff0ba",
        )
    )
}