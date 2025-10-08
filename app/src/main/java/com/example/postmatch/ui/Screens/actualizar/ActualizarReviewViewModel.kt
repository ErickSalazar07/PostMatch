package com.example.postmatch.ui.Screens.actualizarReview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.local.LocalPartidoProvider
import com.example.postmatch.data.repository.PartidoRepository
import com.example.postmatch.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActualizarReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val partidoRepository: PartidoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActualizarReviewState())
    val uiState: StateFlow<ActualizarReviewState> = _uiState

    private var currentReviewId: Int = -1

    fun navigateBack(): Boolean {
        return _uiState.value.navigateBack
    }



    private fun loadPartido(idPartido: Int) {
        viewModelScope.launch {
            val result = partidoRepository.getPartidoById(idPartido)
            if (result.isSuccess) {
                _uiState.update { it.copy(partido = result.getOrNull() ?: PartidoInfo()) }
            } else {
                _uiState.update {
                    it.copy(
                        errorMessage = "No se pudo cargar el partido.",
                        partido = PartidoInfo()
                    )
                }
            }
        }
    }

    fun updateResenha(input: String) {
        _uiState.update { it.copy(resenha = input) }
    }

    fun updateCalificacion(input: Int) {
        _uiState.update { it.copy(calificacion = input) }
    }

    fun updateTitulo(input: String) {
        _uiState.update { it.copy(titulo = input) }
    }

    private fun showState() {
        Log.d("ActualizarReviewViewModel", "titulo: ${_uiState.value.titulo}")
        Log.d("ActualizarReviewViewModel", "resenha: ${_uiState.value.resenha}")
        Log.d("ActualizarReviewViewModel", "calificacion: ${_uiState.value.calificacion}")
    }




    fun resetNavigation() {
        _uiState.update { it.copy(navigateBack = false) }
    }

    init {
        _uiState.update { it.copy(partido = LocalPartidoProvider.partidos[0]) }
    }
}