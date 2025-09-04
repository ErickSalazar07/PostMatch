package com.example.postmatch.ui.configuracionPerfil

import androidx.lifecycle.ViewModel
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.local.LocalSeccionConfiguracionProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ConfiguracionPerfilViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(ConfiguracionPerfilState())
    val uiState: StateFlow<ConfiguracionPerfilState> = _uiState

    fun updateUsuario(usuario: UsuarioInfo) {
        _uiState.update { it.copy(usuario = usuario) }
    }

    fun setCerrarSesionButtonClick(onClick: () -> Unit) {
        _uiState.update { it.copy(cerrarSesionButtonClick = onClick) }
    }

    fun cerrarSesionButtonClick() {
        _uiState.value.cerrarSesionButtonClick()
    }

    init {
        _uiState.update { it.copy(secciones = LocalSeccionConfiguracionProvider.seccionesConfiguracion) }
    }
}