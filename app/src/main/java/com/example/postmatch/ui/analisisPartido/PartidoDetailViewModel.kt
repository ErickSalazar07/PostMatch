package com.example.postmatch.ui.analisisPartido

import androidx.lifecycle.ViewModel
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.ReseniaAnalisisPartidoInfo
import com.example.postmatch.data.ReviewInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PartidoDetailViewModel: ViewModel()  {
    private val _uiState = MutableStateFlow(PartidoDetailState())
    val uiState: StateFlow<PartidoDetailState> = _uiState

    fun setPartido(partido: PartidoInfo) {
       _uiState.update { it.copy(partido = partido) }
    }

    fun updateResenias(resenias: List<ReviewInfo>) {
        _uiState.update { it.copy(resenias = resenias) }
    }


}