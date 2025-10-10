package com.example.postmatch.ui.Screens.modificarPerfil

data class ModificarPerfilState(
    val nombre: String = "",
    val email: String = "",
    val password: String = "",
    val urlFotoPerfil: String = "",
    val registrarButtonClick: () -> Unit = {},
    val errorMessage: String? = null
)

