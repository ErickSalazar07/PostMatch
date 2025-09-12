package com.example.postmatch.ui.perfil

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.R
import com.example.postmatch.data.local.LocalReviewProvider
import com.example.postmatch.data.repository.AuthRepository
import com.example.postmatch.data.repository.StorageRepository
import com.example.postmatch.ui.partidos.PartidosState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// PerfilViewModel.kt
@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val storageRepository: StorageRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilState(
        fotoPerfilUrl = authRepository.currentUser?.photoUrl?.toString() ?: ""
    ))
    val uiState: StateFlow<PerfilState> = _uiState

    // fun updateProfileImageUri(profileImageUri: Uri) {
    //     _uiState.update { it.copy(fotoPerfilUri = profileImageUri) }
    // }

    fun uploadProfileImageToFirebase(uri: Uri) {
        viewModelScope.launch {
            val result = storageRepository.uploadProfileImage(uri)
            if(result.isSuccess) {
                _uiState.update { it.copy(fotoPerfilUrl = result.getOrNull()) }
            }
        }
    }

    init {
        _uiState.update {
            it.copy(resenhias = LocalReviewProvider.reviews)
        }
    }
}
