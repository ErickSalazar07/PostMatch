package com.example.postmatch.ui.partidoDetail

import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalPartidoProvider
import com.example.postmatch.data.local.LocalReviewProvider

data class PartidoDetailState(
    val partido: PartidoInfo = PartidoInfo(),
    val resenias: List<ReviewInfo> = emptyList()
)
