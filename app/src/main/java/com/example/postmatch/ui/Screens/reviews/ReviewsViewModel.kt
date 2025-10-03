package com.example.postmatch.ui.Screens.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalReviewProvider
import com.example.postmatch.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ReviewsState())
    val uiState: StateFlow<ReviewsState> = _uiState

    fun getAllReviews(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = reviewRepository.getReviews()
            if (result.isSuccess) {
                _uiState.update { it.copy(reviews = result.getOrNull() ?: emptyList(), isLoading = true, errorMessage = null) }
            }else{
                _uiState.update { it.copy(errorMessage = result.exceptionOrNull()?.message, isLoading = false)}
            }
        }
    }

    fun updateReviews(input: List<ReviewInfo>) {
        _uiState.update { it.copy(reviews = input) }
    }

    init {
        getAllReviews()
    }
}