package com.example.postmatch.ui.Buscador

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.postmatch.data.local.LocalReviewProvider
import com.example.postmatch.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class BuscarViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(BuscarUIState())
    val uiState: StateFlow<BuscarUIState> = _uiState
    fun onBuscar(query: String) {
        _uiState.value = _uiState.value.copy(
            query = query,
            isLoading = true
        )

        viewModelScope.launch {
            delay(500)
            val resultados = LocalReviewProvider.reviews.filter {
                it.titulo.contains(query, ignoreCase = true) ||
                        it.partidoNombre.contains(query, ignoreCase = true)
            }.map { reviewInfo ->
                Reseña(
                    id = reviewInfo.idReview,
                    titulo = reviewInfo.titulo,
                    autor = reviewInfo.usuarioNombre,
                    fecha = reviewInfo.fecha,
                    equipos = reviewInfo.partidoNombre,
                    resumen = reviewInfo.descripcion,
                    rating = reviewInfo.calificacion.toDouble(),
                    reviews = reviewInfo.numComentarios
                )
            }

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                reseñas = resultados
            )

        }
    }

}
