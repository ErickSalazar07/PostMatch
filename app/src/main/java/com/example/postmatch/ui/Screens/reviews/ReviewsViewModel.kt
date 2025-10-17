package com.example.postmatch.ui.Screens.reviews

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.example.postmatch.data.local.LocalReviewProvider
import com.example.postmatch.data.repository.AuthRepository
import com.example.postmatch.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val authRemoteDataSource: AuthRemoteDataSource
): ViewModel() {

    private val _uiState = MutableStateFlow(ReviewsState())
    val uiState: StateFlow<ReviewsState> = _uiState

    fun getAllReviews(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = reviewRepository.getReviews()
            if (result.isSuccess) {
                _uiState.update { it.copy(reviews = result.getOrNull() ?: emptyList(), isLoading = false, errorMessage = null) }
            }else{
                _uiState.update { it.copy(errorMessage = result.exceptionOrNull()?.message, isLoading = false)}
            }
        }
    }

    fun updateReviews(input: List<ReviewInfo>) {
        _uiState.update { it.copy(reviews = input) }
    }

    fun sendOrDeleteLike(reviewId: String) {
        val userId = authRemoteDataSource.currentUser?.uid?:""
        Log.d("LIKES_DEBUG", "ViewModel -> Iniciando sendOrDeleteLike con reviewId=$reviewId y userId=$userId")
        viewModelScope.launch {
            val result = reviewRepository.sendOrDeleteLike(reviewId, userId)
            Log.d("LIKES_DEBUG", "ViewModel -> Resultado del repositorio: $result")

            if (result.isSuccess) {
                Log.d("LIKES_DEBUG", "Like enviado/eliminado correctamente")
                _uiState.update { currentState ->
                    val updatedReviews = currentState.reviews.map { review ->
                        if (review.idReview == reviewId) {
                            val isLiked = !review.likedByUser
                            val newLikes = if (isLiked) review.numLikes + 1 else review.numLikes - 1
                            Log.d("LIKES_DEBUG", "Actualizando reviewId=$reviewId con newLikes=$newLikes y liked=$isLiked")

                            review.copy(
                                numLikes = newLikes,
                                likedByUser = isLiked
                            )
                        } else review
                    }

                    currentState.copy(reviews = updatedReviews)
                }
            } else {
                Log.e("LIKES_DEBUG", "Error al enviar/eliminar like: ${result.exceptionOrNull()?.message}")
                _uiState.update {
                    it.copy(errorMessage = "Error al enviar el like")
                }
            }
        }
    }

    init {
        _uiState.update { it.copy(reviews = emptyList()) }
    }
}