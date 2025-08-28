package com.example.postmatch.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState
    fun updateUsuario(input: String) {
        _uiState.update { it.copy(usuario = input) }
    }

    fun updateCorreo(input: String) {
        _uiState.update { it.copy(correo = input) }
    }

    fun updatePassword(input: String) {
        _uiState.update { it.copy(password = input) }
    }

    fun changePasswordVisible() {
        _uiState.update { it.copy(passwordVisible = !_uiState.value.passwordVisible) }
    }

    private fun showState() {
        Log.d("LoginViewModel", "usuario: ${_uiState.value.usuario}")
        Log.d("LoginViewModel", "correo: ${_uiState.value.correo}")
        Log.d("LoginViewModel", "password: ${_uiState.value.password}")
    }
}