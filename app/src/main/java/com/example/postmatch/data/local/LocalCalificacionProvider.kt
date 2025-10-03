package com.example.postmatch.data.local

import com.example.postmatch.data.CalificacionInfo

object LocalCalificacionProvider {
    var calificaciones = listOf(
        CalificacionInfo(
            idCalificacion = "1",
            calificacion = 4,
            fecha = "07/08/2025",
            usuarioNombre = "Pedro",
            usuarioEmail = "p@noemail",
            usuarioPassword = "54321",
            usuarioFotoPerfil = "https://firebasestorage.googleapis.com/v0/b/postmatch-c2ddd.firebasestorage.app/o/profileImages%2Fsan_pp.jpg?alt=media&token=ac786d04-b728-4d57-9b99-a975cb4a2ae8",
            reviewTitulo = "Mi opinion sobre FCB vs RM",
            reviewDescripcion = "Partido raro por parte del FCB",
            reviewFecha = "06/08/2025"
        )
    )
}