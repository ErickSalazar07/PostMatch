package com.example.postmatch.ui.Screens.subirHistoria

data class SubirHistoriaState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val historiaSubida: Boolean = false
)