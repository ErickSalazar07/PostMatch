package com.example.postmatch.ui.Screens.crearReview

import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalPartidoProvider

data class CrearReviewState(


    val resenha: String = "",
    val titulo: String = "",
    val partido: PartidoInfo = PartidoInfo(),
    val calificacion: Int = 1,




    //val reviewInfo: ReviewInfo,
    val navigateBack: Boolean = false,
    val errorMessage: String? = null
)




