package com.example.postmatch.ui.Screens.modificarPerfil

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.repository.AuthRepository
import com.example.postmatch.data.repository.UsuarioRepository
import com.example.postmatch.ui.Screens.registro.RegistroState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModificarPerfilViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UsuarioRepository
): ViewModel()  {

    private val _uiState = MutableStateFlow(ModificarPerfilState())
    val uiState: StateFlow<ModificarPerfilState> = _uiState

    private var onUpdateSuccess: (() -> Unit)? = null

    // 🔹 Actualizadores de campos individuales
    fun updateNombre(nuevoNombre: String) {
        _uiState.update { it.copy(nombre = nuevoNombre) }
    }

    fun updateEmail(nuevoEmail: String) {
        _uiState.update { it.copy(email = nuevoEmail) }
    }

    fun updatePassword(nuevaPassword: String) {
        _uiState.update { it.copy(password = nuevaPassword) }
    }

    fun updateUrlFotoPerfil(nuevaUrlFotoPerfil: String) {
        _uiState.update { it.copy(urlFotoPerfil = nuevaUrlFotoPerfil) }
    }

    // 🔹 Nueva función para actualizar el usuario en Firestore

    fun loadUserData(userId: String) {
        viewModelScope.launch {
            try {
                // 🔹 Convertimos el userId a Int (tu repositorio lo usa así)
                val idUsuario = userId.toIntOrNull()
                if (idUsuario == null) {
                    _uiState.update { it.copy(errorMessage = "ID de usuario inválido") }
                    return@launch
                }

                // 🔹 Llamamos al repositorio
                val result = userRepository.getUsuarioById(idUsuario)

                if (result.isSuccess) {
                    val usuario = result.getOrNull()
                    if (usuario != null) {
                        _uiState.update {
                            it.copy(
                                nombre = usuario.nombre ?: "",
                                email = usuario.email ?: "",
                                //urlFotoPerfil = usuario.urlFotoPerfil ?: "",
                                errorMessage = null
                            )
                        }
                    } else {
                        _uiState.update { it.copy(errorMessage = "No se encontró el usuario") }
                    }
                } else {
                    _uiState.update { it.copy(errorMessage = "Error al obtener datos del usuario") }
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Error al cargar datos: ${e.message}") }
            }
        }
    }

    fun updateUser() {
        val state = _uiState.value
        val nombre = state.nombre.trim()
        val email = state.email.trim()
        val password = state.password.trim()

        // Validaciones básicas
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "Todos los campos son obligatorios") }
            return
        }

        if (password.length < 6) {
            _uiState.update { it.copy(errorMessage = "La contraseña debe tener al menos 6 caracteres") }
            return
        }

        val userId = authRepository.currentUser?.uid
        if (userId == null) {
            _uiState.update { it.copy(errorMessage = "No hay usuario autenticado") }
            return
        }

        // 🔹 Llamada al repositorio
        viewModelScope.launch {
            try {
                val result = userRepository.updateUser(
                    userId = userId,
                    nombre = nombre,
                    email = email,
                    password = password
                )

                if (result.isSuccess) {
                    _uiState.update { it.copy(errorMessage = null) }
                    onUpdateSuccess?.invoke()
                } else {
                    _uiState.update { it.copy(errorMessage = "Error al actualizar el perfil") }
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Error: ${e.message}") }
            }
        }
    }

    fun setOnUpdateSuccess(callback: () -> Unit) {
        onUpdateSuccess = callback
    }

    fun showState() {
        Log.d("ModificarPerfilViewModel", "Nombre: ${uiState.value.nombre}")
        Log.d("ModificarPerfilViewModel", "Email: ${uiState.value.email}")
        Log.d("ModificarPerfilViewModel", "Password: ${uiState.value.password}")
        Log.d("ModificarPerfilViewModel", "URL Foto Perfil: ${uiState.value.urlFotoPerfil}")
    }

}