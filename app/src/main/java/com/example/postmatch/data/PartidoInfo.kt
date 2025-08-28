package com.example.postmatch.data

data class PartidoInfo(
    var idPartido: Int,
    var nombre: String,
    var categoria: String,
    var visitante: String,
    var local: String,
    var golesVisitante: Int,
    var golesLocal: Int,
    var posesionLocal: Int,
    var posesionVisitante: Int,
    var tirosLocal: Int,
    var tirosVisitante: Int,
    var fecha: String
)
