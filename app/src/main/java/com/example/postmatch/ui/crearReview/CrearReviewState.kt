package com.example.postmatch.ui.crearReview

import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.local.LocalPartidoProvider

data class CrearReviewState(
    val resenha: String = "",
    val partido: PartidoInfo = LocalPartidoProvider.partidos[0],
    val calificacion: Int = 1,
    val publicarButtonClick: () -> Unit = {}
)
