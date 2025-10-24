package com.example.postmatch.data

data class UsuarioInfo(
   var idUsuario: String = "",
   var nombre: String = "",
   var email: String = "",
   var password: String = "",
   var fotoPerfil: String = "",
   var numFollowed: Int = 0,
   var numFollowers: Int = 0,
   val followed : Boolean = false,
   val historiaActiva : Boolean = false
)
