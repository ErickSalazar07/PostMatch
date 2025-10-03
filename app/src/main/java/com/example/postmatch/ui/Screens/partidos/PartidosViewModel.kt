package com.example.postmatch.ui.Screens.partidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.local.LocalPartidoProvider
import com.example.postmatch.data.repository.PartidoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PartidosViewModel @Inject constructor(
    private val partidoRepository: PartidoRepository
): ViewModel() {
    private var _uiState = MutableStateFlow(PartidosState())
    val uiState: StateFlow<PartidosState> = _uiState

    fun updatePartidos(partidos: List<PartidoInfo>) {
        _uiState.update { it.copy(partidos = partidos)  }
    }

    init {
        viewModelScope.launch {
            val result = partidoRepository.getPartidos()
            if (result.isSuccess) {
                _uiState.update { it.copy(partidos = result.getOrNull() ?: emptyList()) }
            } else {
                _uiState.update { it.copy(partidos = emptyList()) }
            }
        }
        _uiState.update { it.copy(partidos = LocalPartidoProvider.partidos) }
    }
}