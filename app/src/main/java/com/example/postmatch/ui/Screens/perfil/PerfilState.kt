package com.example.postmatch.ui.Screens.perfil

import android.net.Uri
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.dtos.ReviewDto

data class PerfilState(
    val usuarioInfo: UsuarioInfo = UsuarioInfo(),
    val reviews: List<ReviewInfo> = emptyList(),
    val fotoPerfilUrl: String? = null,
    val isCurrentUser: Boolean = false
)


