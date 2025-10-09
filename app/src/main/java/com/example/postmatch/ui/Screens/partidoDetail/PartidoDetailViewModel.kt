package com.example.postmatch.ui.Screens.partidoDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.local.LocalPartidoProvider
import com.example.postmatch.data.local.LocalReviewProvider
import com.example.postmatch.data.repository.PartidoRepository
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

    fun setPartidoInfo(idPartido: Int) {
        viewModelScope.launch {
            val result = partidoRepository.getPartidoById(idPartido)
            if (result.isSuccess) {
                _uiState.update { it.copy(partido = result.getOrNull() ?: PartidoInfo()) }
                val reviewResult = partidoRepository.getReviewsByPartidoId(idPartido)
                if (reviewResult.isSuccess) {
                    _uiState.update { it.copy(reviews = reviewResult.getOrNull() ?: emptyList()) }
                } else {
                    _uiState.update { it.copy(reviews = emptyList()) }
                }
            } else {
                _uiState.update { it.copy(partido = PartidoInfo()) }
            }
        }
    }

    init {
        _uiState.update {
            it.copy(
                partido = LocalPartidoProvider.partidos[0],
                reviews = LocalReviewProvider.reviews
            )
        }
    }
}