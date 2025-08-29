package com.example.postmatch.ui.reviewDetail

import com.example.postmatch.data.ComentarioInfo
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalComentarioProvider
import com.example.postmatch.data.local.LocalReviewProvider

data class ReviewDetailState(
    val comentarios: List<ComentarioInfo> = emptyList(),
    val reviewInfo: ReviewInfo = ReviewInfo()
)
