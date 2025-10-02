package com.example.postmatch.ui.Screens.configuracionPerfil

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.local.LocalSeccionConfiguracionProvider
import com.example.postmatch.data.repository.AuthRepository
import com.example.postmatch.data.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ConfiguracionPerfilViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val storageRepository: StorageRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ConfiguracionPerfilState(
        fotoPerfilUrl = authRepository.currentUser?.photoUrl?.toString() ?: ""
    ))
    val uiState: StateFlow<ConfiguracionPerfilState> = _uiState
    private var onLogout: (() -> Unit)? = null

    fun setOnLogout(callback: () -> Unit) {
        onLogout = callback
    }
    /*
    fun updateUsuario(usuario: UsuarioInfo) {
        _uiState.update { it.copy(usuario = usuario) }
    }

     */

    fun setCerrarSesionButtonClick(onClick: () -> Unit) {
        _uiState.update { it.copy(cerrarSesionButtonClick = onClick) }
    }


    // 🚀 Nueva función para subir imagen al Storage y actualizar estado
    fun uploadProfileImageToFirebase(uri: Uri) {
        viewModelScope.launch {
            val result = storageRepository.uploadProfileImage(uri)
            if (result.isSuccess) {
                _uiState.update { it.copy(fotoPerfilUrl = result.getOrNull()) }
            }
        }
    }
    fun cerrarSesionButtonClick() {
        authRepository.signOut()
        onLogout?.invoke()
    }

    init {
        _uiState.update { it.copy(secciones = LocalSeccionConfiguracionProvider.seccionesConfiguracion) }
    }
}