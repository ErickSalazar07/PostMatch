package com.example.postmatch.ui.partidos

import androidx.lifecycle.ViewModel
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.local.LocalPartidoProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PartidosViewModel: ViewModel() {
    private var _uiState = MutableStateFlow(PartidosState())
    val uiState: StateFlow<PartidosState> = _uiState

    fun updatePartidos(partidos: List<PartidoInfo>) {
        _uiState.update { it.copy(partidos = partidos)  }
    }

    init {
        _uiState.update { it.copy(partidos = LocalPartidoProvider.partidos) }
    }
}