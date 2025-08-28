package com.example.postmatch.ui.analisisPartido

import androidx.lifecycle.ViewModel
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.ReseniaAnalisisPartidoInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AnalisisPartidoViewModel: ViewModel()  {
    private val _uiState = MutableStateFlow(AnalisisPartidoState())
    val uiState: StateFlow<AnalisisPartidoState> = _uiState

    fun updatePartido(partido: PartidoInfo) {
       _uiState.update { it.copy(partido = partido) }
    }

    fun updateResenias(resenias: List<ReseniaAnalisisPartidoInfo>) {
        _uiState.update { it.copy(resenias = resenias) }
    }


}