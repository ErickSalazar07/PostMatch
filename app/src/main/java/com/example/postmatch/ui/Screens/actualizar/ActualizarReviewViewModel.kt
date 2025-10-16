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
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActualizarReviewState())
    val uiState: StateFlow<ActualizarReviewState> = _uiState

    fun navigateBack(): Boolean = _uiState.value.navigateBack

    fun updateDescripcion(input: String) {
        _uiState.update { it.copy(descripcion = input) }
    }

    fun setReview(idReview: String) {
        viewModelScope.launch {
            val result = reviewRepository.getReviewById(idReview)
            if (result.isSuccess) {
                val review = result.getOrNull()
                if (review != null) {
                    /*val partidoResult = partidoRepository.getPartidoById(review.)
                    if (partidoResult.isSuccess) {
                        val partido = partidoResult.getOrNull()
                        if (partido != null) {
                            _uiState.update {
                                it.copy(
                                    titulo = review.titulo,
                                    descripcion = review.descripcion,
                                    calificacion = review.calificacion,
                                    partido = partido,
                                    updateReview = review
                                )
                            }
                        }
                    } else {
                        _uiState.update { it.copy(errorMessage = "No se encontró la reseña") }
                    }*/
                } else {
                    _uiState.update { it.copy(errorMessage = "Error al cargar la reseña") }
                }
            }
        }
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
    fun onUpdateReview() {
        viewModelScope.launch {
            _uiState.value.updateReview.partidoId = _uiState.value.partido.idPartido
            _uiState.value.updateReview.usuarioId = ""
            _uiState.value.updateReview.titulo = _uiState.value.titulo
            _uiState.value.updateReview.descripcion = _uiState.value.descripcion
            _uiState.value.updateReview.calificacion = _uiState.value.calificacion

            val result = reviewRepository.updateReview(_uiState.value.updateReview)
            if (result.isSuccess) {
                _uiState.update { it.copy(navigateBack = true) }
            } else {
                _uiState.update { it.copy(errorMessage = "Error al actualizar la reseña") }
                Log.d("ActualizarReviewViewModel", "Error al actualizar la reseña: ${result.exceptionOrNull()}")
            }
        }
    }
}
