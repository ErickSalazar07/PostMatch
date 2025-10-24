package com.example.postmatch.data.dtos

import com.example.postmatch.data.Historia
import com.google.firebase.Timestamp
import java.util.concurrent.TimeUnit
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

data class HistoriaDTO (
    val idHistoria : String,
    val idUsuario  : String,
    val imagenHistoria : String,
    val timestamp: Timestamp
){
    constructor(): this("", "", "", Timestamp(0,0))
}

fun HistoriaDTO.toHistoria() : Historia
{
    return Historia(
        idHistoria = idHistoria,
        idUsuario = idUsuario,
        imagenHistoria = imagenHistoria,
        horasHistoria = retornarHorasHistoria(timestamp)
    )
}

//Diferencia de horas, es decir, las horas que ha estado al aire la historia
fun retornarHorasHistoria(timestamp : Timestamp) : Long{
     if(timestamp == null){
         throw Exception("No hay tiempo de subida")
     }

    val tsMillis = timestamp.toDate().time

    //UTC - 5
    val zone = ZoneId.of("America/Bogota")
    val nowZoned : ZonedDateTime = ZonedDateTime.ofInstant(Instant.now(), zone)
    val nowMillis = nowZoned.toInstant().toEpochMilli()

    val difMillis = nowMillis - tsMillis

    return TimeUnit.MILLISECONDS.toHours(difMillis).coerceAtLeast(0)
}

fun horasComoTexto(horas: Long): String {
    return when {
        horas <= 0 -> "Hace menos de 1 h"
        horas == 1L -> "Hace 1 h"
        else -> "Hace $horas h"
    }
}