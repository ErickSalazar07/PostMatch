package com.example.postmatch.ui.Screens.reviewDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postmatch.data.ComentarioInfo
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalComentarioProvider
import com.example.postmatch.data.local.LocalReviewProvider
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
        val reviewInfo = LocalReviewProvider.reviews.find { it.idReview == idReview.toString() }
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


    fun sendOrDeleteLike(reviewId: String, userId: String){
        viewModelScope.launch {
          val result = reviewRepository.sendOrDeleteLike(reviewId,userId)

            if(result.isSuccess){

             _uiState.update {
                 it.copy()
             }
            }else{



            }


        }

    }
}