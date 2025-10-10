package com.example.postmatch.ui.Screens.perfil

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.R
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.datasource.services.ReviewRetrofitService
import com.example.postmatch.data.local.LocalReviewProvider
import com.example.postmatch.data.repository.AuthRepository
import com.example.postmatch.data.repository.PartidoRepository
import com.example.postmatch.data.repository.ReviewRepository
import com.example.postmatch.data.repository.StorageRepository
import com.example.postmatch.data.repository.UsuarioRepository
import com.example.postmatch.ui.Screens.partidos.PartidosState
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
    // private val storageRepository: StorageRepository,
    private val usuarioRepository: UsuarioRepository,
    private val reviewRetrofitService: ReviewRepository,
    private val partidoRepository: PartidoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilState())
    val uiState: StateFlow<PerfilState> = _uiState
    /*fun uploadProfileImageToFirebase(uri: Uri) {
        viewModelScope.launch {
            val result = storageRepository.uploadProfileImage(uri)
            if(result.isSuccess) {
                _uiState.update { it.copy(fotoPerfilUrl = result.getOrNull()) }
            }
        }
    }*/

    /*
    fun onDeleteReview(idReview: String) {
        val idUsuarioQuemado: Int = 1 // se cambia despues
        viewModelScope.launch {
            val result = reviewRetrofitService.deleteReviewById(idReview)
            val resultReview = usuarioRepository.getReviewsByUsuarioId(idUsuarioQuemado)
            if (result.isSuccess) {
                _uiState.update { it.copy(reviews = resultReview.getOrNull() ?: emptyList()) }
            } else {
                //_uiState.update { it.copy(reviews = emptyList()) }
                Log.e("PerfilViewModel", "Error al eliminar la reseña: ${result.exceptionOrNull()}")
            }
        }
    }

     */

    fun setPartidoInfo(idPartido: Int) {
        viewModelScope.launch {
            val result = partidoRepository.getPartidoById(idPartido)
            if (result.isSuccess) {
                _uiState.update { it.copy(partido = result.getOrNull() ?: PartidoInfo()) }
                val reseniasResult = partidoRepository.getReviewsByPartidoId(idPartido)
                if (reseniasResult.isSuccess) {
                    _uiState.update { it.copy(resenias = reseniasResult.getOrNull() ?: emptyList()) }
                } else {
                    _uiState.update { it.copy(resenias = emptyList()) }
                }
            } else {
                _uiState.update { it.copy(partido = PartidoInfo()) }
            }
        }
    }
    fun onDeleteReview(idReview: String) {
        viewModelScope.launch {
            val result = runCatching { reviewRetrofitService.deleteReviewById(idReview) }

            if (result.isSuccess) {
                _uiState.update { currentState ->
                    val nuevaLista = currentState.reviews.filterNot { it.idReview == idReview }
                    currentState.copy(reviews = nuevaLista)
                }
            } else {
                Log.e("PerfilViewModel", "Error al eliminar la reseña: ${result.exceptionOrNull()}")
                // 👇 aún así la eliminamos localmente si la API falló por body null
                _uiState.update { currentState ->
                    val nuevaLista = currentState.reviews.filterNot { it.idReview == idReview }
                    currentState.copy(reviews = nuevaLista)
                }
            }
        }

    }





    init {
        var idUsuarioQuemado: Int = 1 // se cambia despues
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
