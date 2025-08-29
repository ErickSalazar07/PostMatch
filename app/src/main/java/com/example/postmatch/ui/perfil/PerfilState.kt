package com.example.postmatch.ui.perfil

data class PerfilState(


    val nombre: String = "",
    val arroba: String = "",
    val oficio: String = "",
    val seguidores: Int = 0,
    val seguidos: Int = 0,
    val fotoPerfil: Int? = null,
    val listaResenias: List<ReseniaPerfilData> = emptyList()
)
