package com.example.postmatch.ui.Screens.registro

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.injection.IoDispatcher
import com.example.postmatch.data.repository.AuthRepository
import com.example.postmatch.data.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RegistroViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UsuarioRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel()  {

    private val _uiState = MutableStateFlow(RegistroState())
    val uiState: StateFlow<RegistroState> = _uiState
    private var onRegisterSuccess: (() -> Unit)? = null

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

    /*

    fun register() {
        val state = _uiState.value
        val nombre = state.nombre.trim()
        val email = state.email.trim()
        val password = state.password.trim()
        val urlFoto = state.urlFotoPerfil.trim()

        // validaciones
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "Todos los campos son obligatorios")}
            return
        }
        if (password.length < 6) {
            _uiState.update { it.copy(errorMessage = "La contraseña debe tener al menos 6 caracteres")}
            return
        }

        viewModelScope.launch(ioDispatcher) {
            val result = authRepository.signUp(_uiState.value.email.trim(), _uiState.value.password.trim())
            if (result.isSuccess) {
                showState()
                 val userId = authRepository.currentUser?.uid

                userRepository.registerUser(
                    nombre = state.nombre,
                    email = state.email,
                    userId = userId!!,
                    fotoPerfilUrl = "",
                    password = state.password,
                )
            } else {
                _uiState.update { it.copy(errorMessage = "Error al registrar usuario") }
            }
        }
    }

     */

    fun register() {
        viewModelScope.launch(ioDispatcher) {

            registerUserOnline()
        }

    }

    suspend fun registerUserOnline(){
        val state = _uiState.value
        val nombre = state.nombre.trim()
        val email = state.email.trim()
        val password = state.password.trim()
        val urlFoto = state.urlFotoPerfil.trim()

        // validaciones
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "Todos los campos son obligatorios")}
            return
        }
        if (password.length < 6) {
            _uiState.update { it.copy(errorMessage = "La contraseña debe tener al menos 6 caracteres")}
            return
        }

        val result = authRepository.signUp(_uiState.value.email.trim(), _uiState.value.password.trim())
        if (result.isSuccess) {
            showState()
            val userId = authRepository.currentUser?.uid

            userRepository.registerUser(
                nombre = state.nombre,
                email = state.email,
                userId = userId!!,
                fotoPerfilUrl = "",
                password = state.password,
            )
        } else {
            _uiState.update { it.copy(errorMessage = "Error al registrar usuario") }
        }

    }

    fun setOnRegisterSuccess(callback: () -> Unit) {
        onRegisterSuccess = callback
    }

    fun showState() {
        Log.d("RegistroViewModel","Nombre: ${uiState.value.nombre}")
        Log.d("RegistroViewModel","Email: ${uiState.value.email}")
        Log.d("RegistroViewModel","Password: ${uiState.value.password}")
        Log.d("RegistroViewModel","URL Foto Perfil: ${uiState.value.urlFotoPerfil}")
    }

}