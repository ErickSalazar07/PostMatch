package com.example.postmatch.ui.Screens.crearReview

import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.local.LocalPartidoProvider

data class CrearReviewState(
    val resenha: String = "",
    val titulo: String = "",   // 👈 nuevo campo
    val partido: PartidoInfo = PartidoInfo(),
    val calificacion: Int = 1,
    val publicarButtonClick: () -> Unit = {}
)


