package com.example.postmatch.data.dtos

import java.util.Date

data class UpdateReviewDto(
    var titulo: String = "",
    var descripcion: String = "",
    var fecha: Date = Date(),
    var calificacion: Int = 1,
    var idUsuario: Int = 0,
    var idPartido: Int = 0
)
