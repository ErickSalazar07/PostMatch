package com.example.postmatch.ui.Screens.perfil

import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.UsuarioInfo

data class PerfilState(
    val usuarioInfo: UsuarioInfo = UsuarioInfo(),
    val reviews: List<ReviewInfo> = emptyList(),
)


