package com.example.postmatch.ui.follow

import androidx.lifecycle.ViewModel
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.local.LocalReviewProvider
import com.example.postmatch.data.local.LocalUsuarioProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FollowViewModel: ViewModel()  {
    private val _uiState = MutableStateFlow(FollowState())
    val uiState = _uiState

    fun setUsuario(usuario: UsuarioInfo){
        _uiState.update { it.copy(usuario = usuario) }
    }

    init {
        _uiState.update {
            it.copy(
                usuario = LocalUsuarioProvider.usuarios[0],
                resenhas = LocalReviewProvider.reviews
            )
        }
    }
}