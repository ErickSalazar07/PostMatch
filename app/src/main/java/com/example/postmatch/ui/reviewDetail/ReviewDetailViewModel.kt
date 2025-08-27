package com.example.postmatch.ui.reviewDetail

import androidx.lifecycle.ViewModel
import com.example.postmatch.data.ComentarioInfo
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalComentarioProvider
import com.example.postmatch.data.local.LocalReviewProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReviewDetailViewModel: ViewModel() {
    private val _comentarios = MutableStateFlow(LocalComentarioProvider.comentarios)
    val comentarios: StateFlow<List<ComentarioInfo>> = _comentarios

    private val _reviewInfo = MutableStateFlow(LocalReviewProvider.reviews[0])
    val reviewInfo: StateFlow<ReviewInfo> = _reviewInfo

    private val _comentarioButtonClick = MutableStateFlow({})

    private val _likeButtonClick = MutableStateFlow({})

    fun setReviewInfo(reviewInfo: ReviewInfo) {
        _reviewInfo.value = reviewInfo
    }

    fun setComentarioButtonClick(action: () -> Unit) {
        _comentarioButtonClick.value = action
    }

    fun setLikeButtonClick(action: () -> Unit) {
        _likeButtonClick.value = action

    }

    fun comentarioButtonClick() {
        _comentarioButtonClick.value()
    }

    fun likeButtonClick() {
        _likeButtonClick.value()
    }

    fun updateComentarios(input: List<ComentarioInfo>) {
        _comentarios.value = input
    }

}