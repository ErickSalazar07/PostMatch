package com.example.postmatch.ui.analisisPartido

import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.ReseniaAnalisisPartidoInfo
import com.example.postmatch.data.local.LocalAnalisisPartidoProvider
import com.example.postmatch.data.local.LocalPartidoProvider

data class AnalisisPartidoState(
    val partido: PartidoInfo = LocalPartidoProvider.partidos[0],
    val resenias: List<ReseniaAnalisisPartidoInfo> = LocalAnalisisPartidoProvider.reseniasAnalisisPartido
)
