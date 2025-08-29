package com.example.postmatch.ui.follow

import androidx.lifecycle.ViewModel
import com.example.postmatch.data.UsuarioInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FollowViewModel: ViewModel()  {
    private val _uiState = MutableStateFlow(FollowState())
    val uiState = _uiState

    fun setUsuario(usuario: UsuarioInfo){
        _uiState.update { it.copy(usuario = usuario) }
    }
}