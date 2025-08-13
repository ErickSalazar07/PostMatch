package com.example.postmatch.data.local

import com.example.postmatch.data.ComentarioInfo

object LocalComentarioProvider {
    var comentarios = listOf(
        ComentarioInfo(
            idComentario = 1,
            descripcion = "Buena review",
            fecha = "09/08/2025",
            usuarioNombre = "Pedro",
            usuarioEmail = "p@noemail",
            usuarioPassword = "54321",
            usuarioFotoPerfil = "user1.png",
            reviewTitulo = "Mi opinion sobre FCB vs RM",
            reviewDescripcion = "Partido raro por parte del FCB",
            reviewFecha = "06/08/2025"
        )
    )
}