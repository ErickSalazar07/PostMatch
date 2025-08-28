package com.example.postmatch.ui.reviews

import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalReviewProvider

data class ReviewsState(
    val reviews: List<ReviewInfo> = LocalReviewProvider.reviews,
)
