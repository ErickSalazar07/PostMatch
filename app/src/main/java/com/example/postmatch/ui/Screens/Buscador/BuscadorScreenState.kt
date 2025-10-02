package com.example.postmatch.ui.Screens.Buscador

data class Reseña(

    val id: Int,
    val titulo: String,
    val autor: String,
    val fecha: String,
    val equipos: String,
    val resumen: String,
    val rating: Double,
    val reviews: Int




)
data class BuscarUIState(
    val query: String = "",
    val filtros: List<String> = emptyList(),
    val reseñas: List<Reseña> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)