package com.example.postmatch.ui.Screens.Historia

import com.example.postmatch.data.Historia

data class HistoriaState(
    val historias : List<Historia> = emptyList<Historia>(),
    val isLoading : Boolean = false,
    val error : String? = null
)
