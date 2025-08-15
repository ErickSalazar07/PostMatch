package com.example.postmatch.data

data class OpcionConfiguracionButtonInfo(
    val titulo: String,
    val subtitulo: String,
    val idIcono: Int,
    var onClick: () -> Unit = {}
)
