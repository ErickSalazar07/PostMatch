package com.example.postmatch.ui.Screens.reviews

import androidx.lifecycle.ViewModel
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalReviewProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ReviewsViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(ReviewsState())
    val uiState: StateFlow<ReviewsState> = _uiState

    fun updateReviews(input: List<ReviewInfo>) {
        _uiState.update { it.copy(reviews = input) }
    }

    init {
        _uiState.update { it.copy(reviews = LocalReviewProvider.reviews) }
    }
}