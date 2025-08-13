package com.example.postmatch.data

data class PartidoInfo(
    var idPartido: Int,
    var nombre: String,
    var categoria: String,
    var visitante: String,
    var local: String,
    var golesVisitante: Int,
    var golesLocal: Int,
    var fecha: String
)
