package com.example.postmatch.data.dtos

import com.example.postmatch.data.PartidoInfo

data class LiveMatchDto(
    val fixture: FixtureDto,
    val league: LeagueDto,
    val teams: TeamsDto,
    val goals: GoalsDto
)

data class FixtureDto(
    val id: Int,
    val date: String,
    val status: StatusDto
)

data class StatusDto(val short: String)
data class LeagueDto(val id: Int, val name: String, val country: String, val logo: String)
data class TeamsDto(val home: TeamDto, val away: TeamDto)
data class TeamDto(val name: String, val logo: String)
data class GoalsDto(val home: Int?, val away: Int?)

fun LiveMatchDto.toPartidoInfo(): PartidoInfo {
    // Los logos individuales
    val homeLogo = teams.home.logo ?: ""
    val awayLogo = teams.away.logo ?: ""
    val leagueLogo = league.logo ?: ""

    // Combinamos logos en un solo string separado por punto y coma
    val combinedLogos = listOf(leagueLogo, homeLogo, awayLogo).joinToString(";")

    return PartidoInfo(
        idPartido = fixture.id.toString(),
        nombre = league.name,
        categoria = league.country,
        visitante = teams.away.name,
        local = teams.home.name,
        golesVisitante = goals.away ?: 0,
        golesLocal = goals.home ?: 0,
        posesionLocal = 0,
        posesionVisitante = 0,
        tirosLocal = 0,
        tirosVisitante = 0,
        fecha = fixture.date,
        partidoFotoUrl = combinedLogos // ✅ logos concatenados
    )
}


