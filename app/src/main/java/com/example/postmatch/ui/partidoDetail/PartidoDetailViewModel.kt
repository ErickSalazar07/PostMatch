package com.example.postmatch.ui.partidoDetail

import androidx.lifecycle.ViewModel
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalPartidoProvider
import com.example.postmatch.data.local.LocalReviewProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class PartidoDetailViewModel @Inject constructor(): ViewModel()  {
    private val _uiState = MutableStateFlow(PartidoDetailState())
    val uiState: StateFlow<PartidoDetailState> = _uiState

    fun setPartido(partido: PartidoInfo) {
       _uiState.update { it.copy(partido = partido) }
    }

    fun updateResenias(resenias: List<ReviewInfo>) {
        _uiState.update { it.copy(resenias = resenias) }
    }

    fun setPartidoInfo(idPartido: Int) {
        val partido = LocalPartidoProvider.partidos.find { it.idPartido == idPartido }
        if (partido != null) {
            _uiState.update { it.copy(partido = partido) }
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