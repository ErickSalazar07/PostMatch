package com.example.postmatch.data

data class ReseniaAnalisisPartidoInfo (
    //Identificacion
    var idResenia: String,

    //Datos del post
    var fotoPerfil:String,
    var nSemanas: Int,
    var nombreReseniador: String,
    var nEstrellas: Int,
    var nLikes: Int,
    var resenia:String,
    var isLiked: Boolean = false
)