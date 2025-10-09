package com.example.postmatch.ui.Screens.actualizarReview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.repository.PartidoRepository
import com.example.postmatch.data.repository.ReviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.postmatch.data.dtos.toReviewInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date

@HiltViewModel
class ActualizarReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val partidoRepository: PartidoRepository,
    private val reviewRetrofitService: ReviewRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActualizarReviewState())
    val uiState: StateFlow<ActualizarReviewState> = _uiState

    fun navigateBack(): Boolean = _uiState.value.navigateBack

    fun updateResenha(input: String) {
        _uiState.update { it.copy(resenha = input) }
    }

    fun updateCalificacion(input: Int) {
        _uiState.update { it.copy(calificacion = input) }
    }

    fun updateTitulo(input: String) {
        _uiState.update { it.copy(titulo = input) }
    }

    fun resetNavigation() {
        _uiState.update { it.copy(navigateBack = false) }
    }

    /** Cargar review y partido asociados al reviewId */
    fun onUpdateReview(updatedReview: ReviewInfo) {
        viewModelScope.launch {
            val result = reviewRepository.updateReview(
                idReview = updatedReview.idReview,
                titulo = updatedReview.titulo,
                descripcion = updatedReview.descripcion,
                fecha = Date()
            )

            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        titulo = updatedReview.titulo,
                        resenha = updatedReview.descripcion
                    )
                }
            } else {
                Log.e("ActualizarReviewViewModel", "Error al actualizar reseña: ${result.exceptionOrNull()}")
            }
        }
    }





}
