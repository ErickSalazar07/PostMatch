package com.example.postmatch.ui.Screens.notificaciones

import com.example.postmatch.data.NotificacionInfo
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.local.LocalNotificacionProvider

data class NotificacionesState(
    val notificaciones: List<NotificacionInfo> = emptyList(),


    val usuariosNotificacion: List<UsuarioInfo> = emptyList(),

    // 🔹 Indicador de carga
    val isLoading: Boolean = false,

    // 🔹 Mensaje de error opcional
    val errorMessage: String? = null
)
