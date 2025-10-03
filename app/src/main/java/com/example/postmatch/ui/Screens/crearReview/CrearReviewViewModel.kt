package com.example.postmatch.ui.Screens.crearReview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.dtos.ReviewDto
import com.example.postmatch.data.local.LocalPartidoProvider
import com.example.postmatch.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

@HiltViewModel
class CrearReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CrearReviewState())
    val uiState: StateFlow<CrearReviewState> = _uiState

    fun updateResenha(input: String) {
        _uiState.update { it.copy(resenha = input) }
    }

    fun updateCalificacion(input: Int) {
        _uiState.update { it.copy(calificacion = input) }
    }

    fun setPublicarButtonClick(action: () -> Unit) {
        _uiState.update { it.copy(publicarButtonClick = action) }
    }

    fun updateTitulo(input: String) {
        _uiState.update { it.copy(titulo = input) }
    }

    private fun showState() {
        Log.d("CrearReviewViewModel", "resenha: ${_uiState.value.resenha}")
        Log.d("CrearReviewViewModel", "calificacion: ${_uiState.value.calificacion}")
    }

    fun publicarButtonClick(onSuccess: () -> Unit = {}) {
        Log.d("CrearReviewViewModel", "publicarButtonClick")
        showState()
        createReview(onSuccess)
    }

    private fun createReview(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val result = reviewRepository.createReview(
                    titulo = _uiState.value.titulo.ifBlank { "Review de ${_uiState.value.partido.nombre}" },
                    descripcion = _uiState.value.resenha,
                    fecha = java.util.Date(),
                    idUsuario = 1,
                    idPartido = 1
                )

                if (result.isSuccess) {
                    Log.d("CrearReviewViewModel", "Review creada con éxito")
                    onSuccess()
                } else {
                    Log.e("CrearReviewViewModel", "Error al crear review", result.exceptionOrNull())
                }
            } catch (e: Exception) {
                Log.e("CrearReviewViewModel", "Excepción al crear review", e)
            }
        }
    }

    init {
        _uiState.update { it.copy(partido = LocalPartidoProvider.partidos[0]) }
    }



}
