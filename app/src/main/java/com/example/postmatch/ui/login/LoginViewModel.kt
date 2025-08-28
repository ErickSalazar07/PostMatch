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

    fun setLoginButtonClick(action: () -> Unit) {
        _uiState.update { it.copy(loginButtonClick = action) }
    }

    fun setSignUpButtonClick(action: () -> Unit) {
        _uiState.update { it.copy(signUpButtonClick = action) }
    }

    private fun shoState() {
        Log.d("LoginViewModel", "usuario: ${_uiState.value.usuario}")
        Log.d("LoginViewModel", "correo: ${_uiState.value.correo}")
        Log.d("LoginViewModel", "password: ${_uiState.value.password}")
    }

    fun loginButtonClick() {
        Log.d("LoginViewModel", "loginButtonClick")
        shoState()
        _uiState.value.loginButtonClick()
    }

    fun signUpButtonClick() {
        Log.d("LoginViewModel", "signUpButtonClick")
        shoState()
        _uiState.value.signUpButtonClick()
    }
}