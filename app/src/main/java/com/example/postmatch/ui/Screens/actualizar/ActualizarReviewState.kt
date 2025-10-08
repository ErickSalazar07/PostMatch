
package com.example.postmatch.ui.Screens.actualizarReview

import com.example.postmatch.data.PartidoInfo

data class ActualizarReviewState(
    val partido: PartidoInfo = PartidoInfo(),
    val titulo: String = "",
    val resenha: String = "",
    val calificacion: Int = 0,
    val navigateBack: Boolean = false,
    val errorMessage: String? = null
)