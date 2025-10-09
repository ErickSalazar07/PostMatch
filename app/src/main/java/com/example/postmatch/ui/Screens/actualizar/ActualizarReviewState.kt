
package com.example.postmatch.ui.Screens.actualizarReview

import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.dtos.CreateReviewDto

data class ActualizarReviewState(
    val partido: PartidoInfo = PartidoInfo(),
    val updateReview: ReviewInfo = ReviewInfo(),
    val titulo: String = "",
    val descripcion: String = "",
    val calificacion: Int = 1,
    val navigateBack: Boolean = false,
    val errorMessage: String? = null
)