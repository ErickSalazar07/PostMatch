package com.example.postmatch.data

data class FollowInfo(
    var idFollow: Int,
    var fecha: String,
// datos usuario FOLLOWER
    var usuarioFollowerNombre: String,
    var usuarioFollowerEmail: String,
    var usuarioFollowerPassword: String,
    var usuarioFollowerFotoPerfil: String,
// datos usuario FOLLOWED
    var usuarioFollowedNombre: String,
    var usuarioFollowedEmail: String,
    var usuarioFollowedPassword: String,
    var usuarioFollowedFotoPerfil: String,
)
