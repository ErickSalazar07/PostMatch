package com.example.postmatch.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val authRepository: AuthRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    private var loginButtonClick: () -> Unit = {}
    private var singInButtonClick: () -> Unit = {}
    val uiState: StateFlow<LoginState> = _uiState

    fun updateUsuario(input: String) {
        _uiState.update { it.copy(usuario = input) }
    }

    fun setLoginButtonClick(callback: () -> Unit) {
        loginButtonClick = callback
    }

    fun setSingInButtonClick(callback: () -> Unit) {
        singInButtonClick = callback
    }

    fun singInButtonClick() {
        showState()
        viewModelScope.launch {
            try {
                authRepository.signUp(_uiState.value.correo, _uiState.value.password)
                singInButtonClick()
            } catch(e: Exception) {
                Log.d("LoginViewModel", e.toString())
            }
        }
    }

    fun loginButtonClick() {
        showState()
        viewModelScope.launch {
            try {
                authRepository.signIn(_uiState.value.correo, _uiState.value.password)
                loginButtonClick()
            } catch(e: Exception) {
                Log.d("LoginViewModel", e.toString())
            }
        }
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