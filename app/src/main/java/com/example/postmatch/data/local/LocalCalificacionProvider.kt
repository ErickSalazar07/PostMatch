package com.example.postmatch.data.local

import com.example.postmatch.data.CalificacionInfo

object LocalCalificacionProvider {
    var calificaciones = listOf(
        CalificacionInfo(
            idCalificacion = 1,
            calificacion = 4,
            fecha = "07/08/2025",
            usuarioNombre = "Pedro",
            usuarioEmail = "p@noemail",
            usuarioPassword = "54321",
            usuarioFotoPerfil = "usuario1.png",
            reviewTitulo = "Mi opinion sobre FCB vs RM",
            reviewDescripcion = "Partido raro por parte del FCB",
            reviewFecha = "06/08/2025"
        )
    )
}