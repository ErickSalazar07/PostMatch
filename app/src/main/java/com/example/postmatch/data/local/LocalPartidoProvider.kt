package com.example.postmatch.data.local

import com.example.postmatch.data.PartidoInfo

object LocalPartidoProvider {
    val partidos = listOf(
        PartidoInfo(
            idPartido = 1,
            nombre = "Real Madrid vs FC Barcelona",
            categoria = "LaLiga",
            visitante = "Real Madrid",
            local = "FC Barcelona",
            golesVisitante = 2,
            golesLocal = 3,
            fecha = "20/05/2025"
        )
    )
}