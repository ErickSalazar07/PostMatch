package com.example.postmatch.data

data class ReseniaAnalisisPartidoInfo (
    //Identificacion
    var idResenia: Int,

    //Datos del post
    var fotoPerfil:Int,
    var nSemanas: Int,
    var nombreReseniador: String,
    var nEstrellas: Int,
    var nLikes: Int,
    var resenia:String,
    var isLiked: Boolean = false
)