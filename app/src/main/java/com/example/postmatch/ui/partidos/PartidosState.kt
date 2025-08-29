package com.example.postmatch.ui.partidos

import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.local.LocalPartidoProvider

data class PartidosState(
    val partidos: List<PartidoInfo> = emptyList()
)
