package com.example.postmatch.ui.Screens.notificaciones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.NotificacionInfo
import com.example.postmatch.data.local.LocalNotificacionProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NotificacionesViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(NotificacionesState())
    val uiState: StateFlow<NotificacionesState> = _uiState

    fun updateNotificaciones(input: List<NotificacionInfo>) {
        _uiState.update { it.copy(notificaciones = input) }
    }

    fun getUsuariosNotificacion() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = usuarioRepository.getUsuarios()
            if(result.isSuccess) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        usuariosNotificacion = result.getOrDefault(emptyList())
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Error desconocido") }
            }
        }
    }

    init {
        _uiState.update { it.copy(notificaciones = LocalNotificacionProvider.notificaciones) }
    }
}