package com.example.postmatch.ui.notificaciones

import com.example.postmatch.data.NotificacionInfo
import com.example.postmatch.data.local.LocalNotificacionProvider

data class NotificacionesState(
    val notificaciones: List<NotificacionInfo> = emptyList()
)
