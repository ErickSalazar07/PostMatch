package com.example.postmatch.ui.registro

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class RegistroViewModel @Inject constructor(): ViewModel()  {
    private val _uiState = MutableStateFlow(RegistroState())
    val uiState: StateFlow<RegistroState> = _uiState

    fun updateNombre(nuevoNombre: String) {
        _uiState.update { it.copy(nombre = nuevoNombre) }
    }

    fun updateEmail(nuevoEmail: String) {
        _uiState.update { it.copy(email = nuevoEmail) }
    }

    fun updatePassword(nuevaPassword: String) {
        _uiState.update { it.copy(password = nuevaPassword) }
    }

    fun updateUrlFotoPerfil(nuevaUrlFotoPerfil: String) {
        _uiState.update { it.copy(urlFotoPerfil = nuevaUrlFotoPerfil) }
    }

    fun setRegistrarButtonClick(action: () -> Unit) {
        _uiState.update { it.copy(registrarButtonClick = action) }
    }

    fun showState() {
        Log.d("RegistroViewModel","Nombre: ${uiState.value.nombre}")
        Log.d("RegistroViewModel","Email: ${uiState.value.email}")
        Log.d("RegistroViewModel","Password: ${uiState.value.password}")
        Log.d("RegistroViewModel","URL Foto Perfil: ${uiState.value.urlFotoPerfil}")
    }

    fun regitrarButtonClick() {
        Log.d("RegistroViewModel","registrarButtonClick")
        showState()
        _uiState.value.registrarButtonClick()
    }
}