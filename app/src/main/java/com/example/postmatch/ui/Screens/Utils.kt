package com.example.postmatch.data.repository

fun horasComoTexto(horas: Long): String {
    return when {
        horas <= 0 -> "Hace menos de 1 h"
        horas == 1L -> "Hace 1 h"
        else -> "Hace $horas h"
    }
}