package com.example.postmatch.ui.analisisPartido

import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalPartidoProvider
import com.example.postmatch.data.local.LocalReviewProvider

data class PartidoDetailState(
    val partido: PartidoInfo = LocalPartidoProvider.partidos[0],
    val resenias: List<ReviewInfo> = LocalReviewProvider.reviews
)
