package com.example.postmatch.ui.Screens.live

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.dtos.toPartidoInfo
import com.example.postmatch.data.repository.LiveMatchRepository
import com.example.postmatch.ui.Screens.partidos.PartidosState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveMatchesViewModel @Inject constructor(
    private val repository: LiveMatchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PartidosState())
    val uiState: StateFlow<PartidosState> = _uiState

    init {
        loadLiveMatches()
    }

    private fun loadLiveMatches() {
        viewModelScope.launch {
            val result = repository.getLiveMatches()
            if (result.isSuccess) {
                val partidos = result.getOrNull()?.map { it.toPartidoInfo() } ?: emptyList()
                _uiState.value = PartidosState(partidos)
            } else {
                _uiState.value = PartidosState(emptyList())
            }
        }
    }
}
