package com.example.postmatch.ui.Screens.reviewsFollow

import com.example.postmatch.data.ReviewInfo

data class ReviewsFollowState(

    val reviews: List<ReviewInfo> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
