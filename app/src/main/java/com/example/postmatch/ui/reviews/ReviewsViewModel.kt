package com.example.postmatch.ui.reviews

import androidx.lifecycle.ViewModel
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalReviewProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReviewsViewModel: ViewModel() {
    private val _reviews = MutableStateFlow(LocalReviewProvider.reviews)
    val reviews: StateFlow<List<ReviewInfo>> = _reviews

    private val _onReviewClick = MutableStateFlow<(Int) -> Unit> {}

    fun updateReviews(input: List<ReviewInfo>) {
        _reviews.value = input
    }

    fun setReviewClick(action: (Int) -> Unit) {
        _onReviewClick.value = action
    }

    fun reviewClick(idReview: Int) {
        _onReviewClick.value(idReview)
    }
}