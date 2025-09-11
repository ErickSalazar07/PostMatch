package com.example.postmatch.ui.perfil

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.postmatch.R
import com.example.postmatch.data.local.LocalReviewProvider
import com.example.postmatch.ui.partidos.PartidosState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// PerfilViewModel.kt
@HiltViewModel
class PerfilViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilState())
    val uiState: StateFlow<PerfilState> = _uiState

    fun updateProfileImageUri(profileImageUri: Uri) {
        _uiState.update { it.copy(fotoPerfilUri = profileImageUri) }
    }

    init {
        _uiState.update {
            it.copy(resenhias = LocalReviewProvider.reviews)
        }
    }
}
