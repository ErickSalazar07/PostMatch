package com.example.postmatch.ui.reviews

import androidx.lifecycle.ViewModel
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalReviewProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ReviewsViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ReviewsState())
    val uiState: StateFlow<ReviewsState> = _uiState

    fun updateReviews(input: List<ReviewInfo>) {
        _uiState.update { it.copy(reviews = input) }
    }

    fun setReviewClick(action: (Int) -> Unit) {
        _uiState.update { it.copy(onReviewClick = action) }
    }

    fun reviewClick(idReview: Int) {
        _uiState.value.onReviewClick(idReview)
    }
}