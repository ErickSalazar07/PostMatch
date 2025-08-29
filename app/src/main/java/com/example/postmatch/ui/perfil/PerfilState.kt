package com.example.postmatch.ui.perfil

import com.example.postmatch.data.ReviewInfo

data class PerfilState(


    val nombre: String = "",
    val arroba: String = "",
    val oficio: String = "",
    val seguidores: Int = 0,
    val seguidos: Int = 0,
    val fotoPerfil: Int? = null,
    val resenhias: List<ReviewInfo> = emptyList()
)
