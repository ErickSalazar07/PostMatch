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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val authRemoteDataSource: AuthRemoteDataSource
): ViewModel() {

    private val _uiState = MutableStateFlow(ReviewsState())
    val uiState: StateFlow<ReviewsState> = _uiState

    fun getAllReviews() {
        viewModelScope.launch {
            reviewRepository.getReviewsOnline()
                .catch { e ->
                    _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
                }
                .collect { reviews ->
                    // Evita sobrescribir si el usuario acaba de hacer like
                    val currentReviews = _uiState.value.reviews
                    val mergedReviews = reviews.map { newReview ->
                        val localReview = currentReviews.find { it.idReview == newReview.idReview }
                        if (localReview != null) {
                            // Conserva el valor de likedByUser temporal si no hay diferencia en el conteo
                            if (localReview.likedByUser != newReview.likedByUser &&
                                (localReview.numLikes == newReview.numLikes ||
                                        localReview.numLikes == newReview.numLikes + 1 ||
                                        localReview.numLikes == newReview.numLikes - 1)
                            ) {
                                localReview
                            } else newReview
                        } else newReview
                    }

                    _uiState.update {
                        it.copy(reviews = mergedReviews, isLoading = false)
                    }
                }
        }
    }

    fun updateReviews(input: List<ReviewInfo>) {
        _uiState.update { it.copy(reviews = input) }
    }

    fun sendOrDeleteLike(reviewId: String) {
        val userId = authRemoteDataSource.currentUser?.uid ?: return
        viewModelScope.launch {
            _uiState.update { state ->
                val updated = state.reviews.map {
                    if (it.idReview == reviewId) it.copy(isLiking = true) else it
                }
                state.copy(reviews = updated)
            }
            val result = reviewRepository.sendOrDeleteLike(reviewId, userId)

            if (result.isSuccess) {
                _uiState.update { state ->
                    val updatedReviews = state.reviews.map { review ->
                        if (review.idReview == reviewId) {
                            val newLikedState = !review.likedByUser
                            val newLikes = if (newLikedState) review.numLikes + 1 else review.numLikes - 1
                            review.copy(numLikes = newLikes, likedByUser = newLikedState)
                        } else review
                    }
                    state.copy(reviews = updatedReviews)
                }
            } else {
                _uiState.update { it.copy(errorMessage = "Error al enviar el like") }
            }
        }
    }

    init {
        _uiState.update { it.copy(reviews = emptyList()) }
    }
}