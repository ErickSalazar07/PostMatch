package com.example.postmatch.ui.Screens.login

data class LoginState(
    val usuario: String = "",
    val correo: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val errorMessage: String? = null
)
