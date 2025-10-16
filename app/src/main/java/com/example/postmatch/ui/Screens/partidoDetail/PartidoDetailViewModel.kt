package com.example.postmatch.ui.Screens.partidoDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalPartidoProvider
import com.example.postmatch.data.local.LocalReviewProvider
import com.example.postmatch.data.repository.PartidoRepository
import com.example.postmatch.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PartidoDetailViewModel @Inject constructor(
    private val partidoRepository: PartidoRepository
): ViewModel()  {
    private val _uiState = MutableStateFlow(PartidoDetailState())
    val uiState: StateFlow<PartidoDetailState> = _uiState

    fun setPartido(partido: PartidoInfo) {
       _uiState.update { it.copy(partido = partido) }
    }

    fun updateResenias(resenias: List<ReviewInfo>) {
        _uiState.update { it.copy(resenias = resenias) }
    }

    fun setPartidoInfo(idPartido: String) {
        viewModelScope.launch {
            val result = partidoRepository.getPartidoById(idPartido)
            if (result.isSuccess) {
                _uiState.update { it.copy(partido = result.getOrNull() ?: PartidoInfo()) }
                Log.d("PartidoDetailViewModel", "Partido obtenido: ${_uiState.value.partido}")
            } else {
                _uiState.update { it.copy(partido = PartidoInfo()) }
                Log.d("PartidoDetailViewModel", "Error al obtener el partido: ${result.exceptionOrNull()}")
            }
        }
    }

    init {
        _uiState.update {
            it.copy(
                partido = LocalPartidoProvider.partidos[0],
                resenias = LocalReviewProvider.reviews
            )
        }
    }
}