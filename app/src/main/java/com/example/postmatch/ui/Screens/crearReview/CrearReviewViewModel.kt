package com.example.postmatch.ui.Screens.crearReview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.local.LocalPartidoProvider
import com.example.postmatch.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CrearReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
): ViewModel() {

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

    private fun showState() {
        Log.d("CrearReviewViewModel", "resenha: ${_uiState.value.resenha}")
        Log.d("CrearReviewViewModel", "calificacion: ${_uiState.value.calificacion}")
    }

    fun publicarButtonClick() {
        Log.d("CrearReviewViewModel", "publicarButtonClick")
        showState()
        _uiState.value.publicarButtonClick()
    }

    init {
        _uiState.update { it.copy(partido = LocalPartidoProvider.partidos[0]) }
    }

}