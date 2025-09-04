package com.example.postmatch.ui.reviewDetail

import androidx.lifecycle.ViewModel
import com.example.postmatch.data.ComentarioInfo
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalComentarioProvider
import com.example.postmatch.data.local.LocalReviewProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ReviewDetailViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(ReviewDetailState())
    val uiState: StateFlow<ReviewDetailState> = _uiState

    fun setReviewInfo(idReview: Int) {
        val reviewInfo = LocalReviewProvider.reviews.find { it.idReview == idReview }
        if (reviewInfo != null) {
            _uiState.update { it.copy(reviewInfo = reviewInfo) }
        }
    }

    fun updateComentarios(input: List<ComentarioInfo>) {
        _uiState.update { it.copy(comentarios = input) }
    }

    init {
        _uiState.update { it.copy(comentarios = LocalComentarioProvider.comentarios) }
    }
}