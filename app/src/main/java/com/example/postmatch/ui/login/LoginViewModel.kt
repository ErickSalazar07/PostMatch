package com.example.postmatch.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
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
    private var loginButtonClick: (() -> Unit)? = null
    private var signInButtonClick: () -> Unit = {}
    val uiState: StateFlow<LoginState> = _uiState
    val currentUser: FirebaseUser? = authRepository.currentUser


    fun updateUsuario(input: String) {
        _uiState.update { it.copy(usuario = input) }
    }

    fun setLoginButtonClick(callback: () -> Unit) {
        loginButtonClick = callback
    }

    fun setSignInButtonClick(callback: () -> Unit) {
        signInButtonClick = callback
    }

    fun onSignInButtonClick() {
        signInButtonClick?.invoke()
    }

    fun onLoginButtonClick() {
        showState()
        viewModelScope.launch {
            val result = authRepository.signIn(_uiState.value.correo.trim(), _uiState.value.password.trim())
            if(result.isSuccess) {
                loginButtonClick?.invoke() // navega si login fue OK
                _uiState.update { it.copy(errorMessage = null) }
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Error desconocido"
                Log.e("LoginViewModel", "Login error")
                _uiState.update { it.copy(errorMessage = errorMsg) }
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

    fun logout() {
        authRepository.signOut()
    }


    private fun showState() {
        Log.d("LoginViewModel", "usuario: ${_uiState.value.usuario}")
        Log.d("LoginViewModel", "correo: ${_uiState.value.correo}")
        Log.d("LoginViewModel", "password: ${_uiState.value.password}")
    }
}