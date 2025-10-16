package com.example.postmatch.data.dtos

import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.UsuarioInfo
import java.util.Date

data class PartidoDto(
    val id: String,
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
    val fecha: Date,
    val partidoFotoUrl: String
) {
    constructor(): this("","","","","",0,0,0,0,0,0,Date(),"")
}

fun PartidoDto.toPartidoInfo(): PartidoInfo {
    return PartidoInfo(
        idPartido = id,
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
        fecha = fecha.toString(),
        partidoFotoUrl = partidoFotoUrl
    )
}
