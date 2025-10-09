package com.example.postmatch.ui.Screens.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.repository.ReviewRepository
import com.example.postmatch.data.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// PerfilViewModel.kt
@HiltViewModel
class PerfilViewModel @Inject constructor(
    // private val authRepository: AuthRepository,
    // private val storageRepository: StorageRepository,
    private val usuarioRepository: UsuarioRepository,
    private val reviewRetrofitService: ReviewRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilState())
    val uiState: StateFlow<PerfilState> = _uiState

    /*
    fun uploadProfileImageToFirebase(uri: Uri) {
        viewModelScope.launch {
            val result = storageRepository.uploadProfileImage(uri)
            if(result.isSuccess) {
                _uiState.update { it.copy(fotoPerfilUrl = result.getOrNull()) }
            }
        }
    }
    */

    fun onDeleteReview(idReview: String) {
        viewModelScope.launch {
            val result = runCatching { reviewRetrofitService.deleteReviewById(idReview) }

            if (result.isSuccess) {
                _uiState.update { currentState ->
                    val nuevaLista = currentState.reviews.filterNot { it.idReview == idReview }
                    currentState.copy(reviews = nuevaLista)
                }
            } else {
                _uiState.update { currentState -> // eliminamos localmente falló por body null
                    val nuevaLista = currentState.reviews.filterNot { it.idReview == idReview }
                    currentState.copy(reviews = nuevaLista)
                }
            }
        }

    }

    fun setUsuarioPerfil(idUsuario: Int) {
        viewModelScope.launch {
            val result = usuarioRepository.getUsuarioById(idUsuario)
            if (result.isSuccess) {
                _uiState.update { it.copy(usuarioInfo = result.getOrNull() ?: UsuarioInfo()) }
                val resultReviews = usuarioRepository.getReviewsByUsuarioId(idUsuario)
                if (resultReviews.isSuccess) {
                    _uiState.update { it.copy(reviews = resultReviews.getOrNull() ?: emptyList()) }
                } else {
                    _uiState.update { it.copy(reviews = emptyList()) }
                }
            }
        }
    }

    init {
        val idUsuarioQuemado = 1
        viewModelScope.launch {
            val result = usuarioRepository.getUsuarioById(idUsuarioQuemado)
            if (result.isSuccess) {
                _uiState.update { it.copy(usuarioInfo = result.getOrNull() ?: UsuarioInfo()) }
                val resultReviews = usuarioRepository.getReviewsByUsuarioId(idUsuarioQuemado)
                if (resultReviews.isSuccess) {
                    _uiState.update { it.copy(reviews = resultReviews.getOrNull() ?: emptyList()) }
                } else {
                    _uiState.update { it.copy(reviews = emptyList()) }
                }
            }
        }
    }
}
