package com.example.postmatch.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel: ViewModel() {
    private val _usuario = MutableStateFlow("")
    val usuario: StateFlow<String> = _usuario

    private val _correo = MutableStateFlow("")
    val correo: StateFlow<String> = _correo

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> = _passwordVisible

    private val _loginButtonClick = MutableStateFlow({})

    private val _signUpButtonClick = MutableStateFlow({})

    fun updateUsuario(input: String) {
        Log.d("LoginViewModel", "updateUsuario: $input")
        _usuario.value = input
    }

    fun updateCorreo(input: String) {
        Log.d("LoginViewModel", "updateCorreo: $input")
        _correo.value = input
    }

    fun updatePassword(input: String) {
        Log.d("LoginViewModel", "udpatePassword: $input")
        _password.value = input
    }

    fun changePasswordVisible() {
        Log.d("LoginViewModel", "updatePasswordVisible: $_passwordVisible")
        _passwordVisible.value = !_passwordVisible.value
    }

    fun setLoginButtonClick(action: () -> Unit) {
        _loginButtonClick.value = action
    }

    fun setSignUpButtonClick(action: () -> Unit) {
        _signUpButtonClick.value = action
    }

    fun loginButtonClick() {
        Log.d("LoginViewModel", "loginButtonClick")
        _loginButtonClick.value()
    }

    fun signUpButtonClick() {
        Log.d("LoginViewModel", "signUpButtonClick")
        _signUpButtonClick.value()
    }
}