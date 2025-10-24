package com.example.postmatch.ui.Screens.Historia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postmatch.data.Historia
import com.example.postmatch.data.repository.HistoriaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoriaViewModel @Inject constructor(
    private val historiaRepository: HistoriaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoriaState())
    val uiState : StateFlow<HistoriaState> = _uiState

    fun getHistorias(idUsuario : String){
        viewModelScope.launch {
            val result = historiaRepository.getHistorias( idUsuario )

            if(result.isSuccess){
                _uiState.update { it.copy(historias = result.getOrNull()?: emptyList()) }
            }
            else{
                _uiState.update { it.copy(historias = emptyList()) }
            }
        }
    }
    init {

    }
}