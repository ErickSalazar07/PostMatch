package com.example.postmatch.ui.Screens.notificaciones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postmatch.data.NotificacionInfo
import com.example.postmatch.data.local.LocalNotificacionProvider
import com.example.postmatch.data.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NotificacionesViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(NotificacionesState())
    val uiState: StateFlow<NotificacionesState> = _uiState

    fun getUsuariosNotificacion() {
        viewModelScope.launch {
            val resultUsuariosNotificacion = usuarioRepository.getUsuarios()
            if(resultUsuariosNotificacion.isSuccess) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        usuariosNotificacion = resultUsuariosNotificacion.getOrDefault(emptyList())
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Error desconocido")
                }
            }
        }
    }

    init {
        getUsuariosNotificacion()
    }
}