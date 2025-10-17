package com.example.postmatch.ui.Screens.crearReview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.dtos.CreateReviewDto
import com.example.postmatch.data.local.LocalPartidoProvider
import com.example.postmatch.data.repository.PartidoRepository
import com.example.postmatch.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CrearReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val partidoRepository: PartidoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CrearReviewState())
    val uiState: StateFlow<CrearReviewState> = _uiState

    fun updatePartido(idPartido: String) {
        viewModelScope.launch {
            val result = partidoRepository.getPartidoById(idPartido)
            if (result.isSuccess) {
                _uiState.update { it.copy(
                    partido = result.getOrNull() ?: PartidoInfo()
                ) }
            } else {
                _uiState.update { it.copy(
                    errorMessage = "No se pudo cargar el partido.",
                    partido = PartidoInfo()
                )
                }
            }
        }
    }

    fun updateResenha(input: String) {
        _uiState.update { it.copy(descripcion = input) }
    }

    fun updateCalificacion(input: Int) {
        _uiState.update { it.copy(calificacion = input) }
    }

    fun updateTitulo(input: String) {
        _uiState.update { it.copy(titulo = input) }
    }

    private fun showState() {
        Log.d("CrearReviewViewModel", "resenha: ${_uiState.value.nuevaReview.descripcion}")
        Log.d("CrearReviewViewModel", "calificacion: ${_uiState.value.nuevaReview.calificacion}")
    }

    fun createReview(idPartido: String) {
        Log.d("CrearReviewViewModel", "publicarButtonClick")
        showState()
        viewModelScope.launch {
            try {
                val nuevaReview = CreateReviewDto()
                nuevaReview.idPartido = idPartido
                nuevaReview.titulo = _uiState.value.titulo
                nuevaReview.descripcion = _uiState.value.descripcion
                nuevaReview.calificacion = _uiState.value.calificacion

                val result = reviewRepository.createReview(nuevaReview)
                if (result.isSuccess) {
                    _uiState.update { it.copy(navigateBack = true, errorMessage = null) }
                } else {
                    _uiState.update { it.copy(errorMessage = "No se pudo publicar. Verifica tu conexión.") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Error de conexión con la base de datos.") }
            }
        }
    }

    init {
        _uiState.update { it.copy(partido = LocalPartidoProvider.partidos[0]) }
    }
}
