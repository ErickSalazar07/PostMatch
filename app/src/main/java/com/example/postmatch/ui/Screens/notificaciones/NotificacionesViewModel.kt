package com.example.postmatch.ui.Screens.notificaciones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun updateNotificaciones(input: List<NotificacionInfo>) {
        _uiState.update { it.copy(notificaciones = input) }
    }

    /*

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

     */

    fun getUsuariosNotificacion() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }

                // 🔹 Llamada al repositorio (que ahora usa Firestore)
                val result = usuarioRepository.getUsuarios()

                result.fold(
                    onSuccess = { usuarios ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                usuariosNotificacion = usuarios,
                                errorMessage = null
                            )
                        }
                    },
                    onFailure = { e ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = e.message ?: "Error al obtener usuarios desde Firestore"
                            )
                        }
                    }
                )

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error inesperado al obtener usuarios"
                    )
                }
            }
        }
    }


    init {
        _uiState.update { it.copy(notificaciones = LocalNotificacionProvider.notificaciones) }
    }
}