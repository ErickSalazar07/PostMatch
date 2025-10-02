package com.example.postmatch.ui.Screens.registro

data class RegistroState(
    val nombre: String = "",
    val email: String = "",
    val password: String = "",
    val urlFotoPerfil: String = "",
    val registrarButtonClick: () -> Unit = {},
    val errorMessage: String? = null
)
