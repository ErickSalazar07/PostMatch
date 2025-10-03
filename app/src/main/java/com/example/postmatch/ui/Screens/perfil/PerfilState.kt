package com.example.postmatch.ui.Screens.perfil

import android.net.Uri
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.dtos.ReviewDto

data class PerfilState(


    val nombre: String = "",
    val arroba: String = "",
    val oficio: String = "",
    val seguidores: Int = 0,
    val seguidos: Int = 0,
    val fotoPerfilUrl: String? = null,
    val resenhias: List<ReviewInfo> = emptyList(),
    val resenhiass: List<ReviewDto> = emptyList()
)


