package com.example.postmatch.data.dtos

import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.UsuarioInfo

data class PartidoDto(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val visitante: String,
    val local: String,
    val golesLocal: Int,
    val golesVisitante: Int,
    val posesionLocal: Int,
    val posesionVisitante: Int,
    val tirosLocal: Int,
    val tirosVisitante: Int,
    val fecha: String,
    val partidoFotoUrl: String,
    val createdAt: String,
    val updatedAt: String
)

fun PartidoDto.toPartidoInfo(): PartidoInfo {
    return PartidoInfo(
        idPartido = id.toString(),
        nombre = nombre,
        categoria = categoria,
        visitante = visitante,
        local = local,
        golesVisitante = golesVisitante,
        golesLocal = golesLocal,
        posesionLocal = posesionLocal,
        posesionVisitante = posesionVisitante,
        tirosLocal = tirosLocal,
        tirosVisitante = tirosVisitante,
        fecha = fecha,
        partidoFotoUrl = partidoFotoUrl
    )
}
