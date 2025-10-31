package com.example.postmatch.data

data class PartidoInfo(
    var idPartido: String = "",
    var nombre: String = "",
    var categoria: String = "",
    var visitante: String = "",
    var local: String = "",
    var golesVisitante: Int = 0,
    var golesLocal: Int = 0,
    var posesionLocal: Int = 0,
    var posesionVisitante: Int = 0,
    var tirosLocal: Int = 0,
    var tirosVisitante: Int = 0,
    var fecha: String = "",
    var partidoFotoUrl: String = ""

)
