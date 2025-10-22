package com.example.postmatch.ui.Screens.perfil

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.UsuarioInfo

import com.example.postmatch.data.repository.AuthRepository
import com.example.postmatch.data.repository.ReviewRepository
import com.example.postmatch.data.repository.StorageRepository
import com.example.postmatch.data.repository.UsuarioRepository
import com.google.firebase.auth.FirebaseAuth
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
    private val storageRepository: StorageRepository,
    private val usuarioRepository: UsuarioRepository,
    private val reviewRepository: ReviewRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilState())
    val uiState: StateFlow<PerfilState> = _uiState

    fun uploadProfileImageToFirebase(uri: Uri) {
        viewModelScope.launch {
            val result = storageRepository.uploadProfileImage(uri)
            Log.d("PerfilViewModel", "uploadProfileImageToFirebase: $result")
            if (result.isSuccess) {
                Log.d("PerfilViewModel", "uploadProfileImageToFirebase: ${result.getOrNull()}")
                _uiState.update { it.copy(fotoPerfilUrl = result.getOrNull()) }
            }
        }
    }

    fun onDeleteReview(idReview: String) {
        viewModelScope.launch {
            val result = reviewRepository.deleteReviewById(idReview)
            if (result.isSuccess) {
                _uiState.update { currentState ->
                    val nuevaLista = currentState.reviews.filterNot { it.idReview == idReview }
                    currentState.copy(reviews = nuevaLista)
                }
            } else {
                Log.d("PerfilViewModel", "Error al eliminar la reseña: ${result.exceptionOrNull()}")
            }
        }
    }

    fun getUserInfo(idUsuario: String) {
        viewModelScope.launch {
            val result = usuarioRepository.getUsuarioById(idUsuario)
            if (result.isSuccess) {
                _uiState.update { it.copy(
                    usuarioInfo = result.getOrDefault(UsuarioInfo()),
                    fotoPerfilUrl = it.usuarioInfo.fotoPerfil,
                    isCurrentUser = idUsuario == authRepository.currentUser?.uid
                )}
                val resultReviews = usuarioRepository.getReviewsByUsuarioId(idUsuario)
                if (resultReviews.isSuccess) {
                    _uiState.update { it.copy(reviews = resultReviews.getOrDefault(emptyList())) }
                } else {
                    _uiState.update { it.copy(reviews = emptyList()) }
                    Log.d(
                        "PerfilViewModel",
                        "Error al cargar las reseñas: ${resultReviews.exceptionOrNull()}"
                    )
                }
            } else {
                Log.d(
                    "PerfilViewModel",
                    "Error al cargar el usuario: ${result.exceptionOrNull()}"
                )
            }
        }
    }

    fun cargarUsuarioActual() {
        viewModelScope.launch {
            val firebaseUser = authRepository.currentUser
            if (firebaseUser != null) {
                val perfilUsuarioResult = usuarioRepository.getUsuarioById(firebaseUser.uid)
                if (perfilUsuarioResult.isSuccess) {
                    _uiState.update {
                        it.copy(
                            usuarioInfo = perfilUsuarioResult.getOrDefault(UsuarioInfo()),
                            isCurrentUser = true
                        )
                    }
                } else {
                    Log.d(
                        "PerfilViewModel",
                        "Error al cargar el usuario: ${perfilUsuarioResult.exceptionOrNull()}"
                    )
                }
            } else {
                Log.w("PerfilViewModel", "No hay usuario autenticado")
            }
        }
    }

    fun seguirTantoDejarDeSeguirUsuario(idUsuarioSeguir: String){
        val usuarioActual = FirebaseAuth.getInstance().currentUser?.uid?: ""

        viewModelScope.launch{
            val result = usuarioRepository.seguirTantoDejarDeSeguirUsuario(idUsuarioActual = usuarioActual, idUsuarioSeguir = idUsuarioSeguir)

            if(result.isSuccess){
                _uiState.value = _uiState.value.copy(
                    usuarioInfo = _uiState.value.usuarioInfo.copy(
                        numFollowers = if(_uiState.value.usuarioInfo.followed) _uiState.value.usuarioInfo.numFollowers - 1 else _uiState.value.usuarioInfo.numFollowers + 1,

                        followed = !_uiState.value.usuarioInfo.followed
                    )
                )
            }
        }
    }

    init {
        cargarUsuarioActual()
    }

}