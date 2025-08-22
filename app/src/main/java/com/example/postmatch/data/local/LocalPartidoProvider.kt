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
        ),

        PartidoInfo(
            idPartido = 2,
            nombre = "PSG vs Bayern Munich",
            categoria = "Champions League",
            visitante = "Bayern Munich",
            local = "PSG",
            golesVisitante = 1,
            golesLocal = 2,
            fecha = "21/05/2025"
        ),
        PartidoInfo(
            idPartido = 3,
            nombre = "Liverpool vs Manchester City",
            categoria = "Premier League",
            visitante = "Manchester City",
            local = "Liverpool",
            golesVisitante = 2,
            golesLocal = 2,
            fecha = "19/05/2025"
        ),
        PartidoInfo(
            idPartido = 4,
            nombre = "Juventus vs Inter de Milán",
            categoria = "Serie A",
            visitante = "Inter de Milán",
            local = "Juventus",
            golesVisitante = 0,
            golesLocal = 1,
            fecha = "18/05/2025"
        ),
        PartidoInfo(
            idPartido = 5,
            nombre = "Chelsea vs Arsenal",
            categoria = "Premier League",
            visitante = "Arsenal",
            local = "Chelsea",
            golesVisitante = 3,
            golesLocal = 2,
            fecha = "17/05/2025"
        ),
        PartidoInfo(
            idPartido = 6,
            nombre = "Milan vs Napoli",
            categoria = "Serie A",
            visitante = "Napoli",
            local = "Milan",
            golesVisitante = 1,
            golesLocal = 1,
            fecha = "16/05/2025"
        ),
        PartidoInfo(
            idPartido = 7,
            nombre = "Atlético de Madrid vs Sevilla",
            categoria = "LaLiga",
            visitante = "Sevilla",
            local = "Atlético de Madrid",
            golesVisitante = 0,
            golesLocal = 2,
            fecha = "15/05/2025"
        ),
        PartidoInfo(
            idPartido = 8,
            nombre = "Borussia Dortmund vs RB Leipzig",
            categoria = "Bundesliga",
            visitante = "RB Leipzig",
            local = "Borussia Dortmund",
            golesVisitante = 2,
            golesLocal = 3,
            fecha = "14/05/2025"
        )
    )
}