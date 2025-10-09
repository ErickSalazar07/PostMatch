package com.example.postmatch.ui.Screens.crearReview

import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.dtos.CreateReviewDto
import com.example.postmatch.data.local.LocalPartidoProvider

data class CrearReviewState(


    val nuevaReview: CreateReviewDto = CreateReviewDto(),
    val partido: PartidoInfo = PartidoInfo(),
    val titulo: String = "",
    val descripcion: String = "",
    val calificacion: Int = 1,
    val navigateBack: Boolean = false,
    val errorMessage: String? = null
)




