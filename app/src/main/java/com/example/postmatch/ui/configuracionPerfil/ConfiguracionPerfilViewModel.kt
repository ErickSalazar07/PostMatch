package com.example.postmatch.ui.configuracionPerfil

import androidx.lifecycle.ViewModel
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.local.LocalSeccionConfiguracionProvider
import com.example.postmatch.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ConfiguracionPerfilViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ConfiguracionPerfilState())
    val uiState: StateFlow<ConfiguracionPerfilState> = _uiState
    private var onLogout: (() -> Unit)? = null

    fun setOnLogout(callback: () -> Unit) {
        onLogout = callback
    }

    fun updateUsuario(usuario: UsuarioInfo) {
        _uiState.update { it.copy(usuario = usuario) }
    }

    fun setCerrarSesionButtonClick(onClick: () -> Unit) {
        _uiState.update { it.copy(cerrarSesionButtonClick = onClick) }
    }

    fun cerrarSesionButtonClick() {
        authRepository.signOut()
        onLogout?.invoke()
    }

    init {
        _uiState.update { it.copy(secciones = LocalSeccionConfiguracionProvider.seccionesConfiguracion) }
    }
}