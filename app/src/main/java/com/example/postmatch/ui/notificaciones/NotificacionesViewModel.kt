package com.example.postmatch.ui.notificaciones

import androidx.lifecycle.ViewModel
import com.example.postmatch.data.NotificacionInfo
import com.example.postmatch.data.local.LocalNotificacionProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NotificacionesViewModel: ViewModel() {
    private val _notificaciones = MutableStateFlow(LocalNotificacionProvider.notificaciones)
    val notificaciones: StateFlow<List<NotificacionInfo>> = _notificaciones

    fun updateNotificaciones(input: List<NotificacionInfo>) {
        _notificaciones.value = input
    }
}