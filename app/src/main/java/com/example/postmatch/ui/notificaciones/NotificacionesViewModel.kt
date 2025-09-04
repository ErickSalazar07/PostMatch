package com.example.postmatch.ui.notificaciones

import androidx.lifecycle.ViewModel
import com.example.postmatch.data.NotificacionInfo
import com.example.postmatch.data.local.LocalNotificacionProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class NotificacionesViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(NotificacionesState())
    val uiState: StateFlow<NotificacionesState> = _uiState

    fun updateNotificaciones(input: List<NotificacionInfo>) {
        _uiState.update { it.copy(notificaciones = input) }
    }

    init {
        _uiState.update { it.copy(notificaciones = LocalNotificacionProvider.notificaciones) }
    }
}