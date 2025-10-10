package com.example.postmatch.ui.Screens.notificaciones

import com.example.postmatch.data.NotificacionInfo
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.local.LocalNotificacionProvider

data class NotificacionesState(
    val usuariosNotificacion: List<UsuarioInfo> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
