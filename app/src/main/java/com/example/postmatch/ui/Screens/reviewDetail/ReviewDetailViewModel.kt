package com.example.postmatch.ui.Screens.reviewDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalComentarioProvider
import com.example.postmatch.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewDetailViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ReviewDetailState())
    val uiState: StateFlow<ReviewDetailState> = _uiState

    fun setReviewInfo(idReview: String) {
        viewModelScope.launch {
            val result = reviewRepository.getReviewById(idReview)
            if (result.isSuccess) {
                _uiState.update { it.copy(reviewInfo = result.getOrNull() ?: ReviewInfo()) }
            } else {
                Log.d("ReviewDetailViewModel", "Error: ${result.exceptionOrNull()}")
            }
        }
    }


    init {
        _uiState.update { it.copy(comentarios = LocalComentarioProvider.comentarios) }
    }
}