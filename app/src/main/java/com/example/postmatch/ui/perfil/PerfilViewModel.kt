package com.example.postmatch.ui.perfil

import androidx.lifecycle.ViewModel
import com.example.postmatch.R
import com.example.postmatch.data.local.LocalReviewProvider
import com.example.postmatch.ui.partidos.PartidosState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// PerfilViewModel.kt
class PerfilViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilState())
    val uiState: StateFlow<PerfilState> = _uiState

    init {
        _uiState.update {
            it.copy(resenhias = LocalReviewProvider.reviews)
        }
    }
}
