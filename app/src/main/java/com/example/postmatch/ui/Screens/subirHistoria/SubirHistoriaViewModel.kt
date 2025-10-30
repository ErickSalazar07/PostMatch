package com.example.postmatch.ui.Screens.subirHistoria

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.repository.AuthRepository
import com.example.postmatch.data.repository.HistoriaRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubirHistoriaViewModel @Inject constructor(
    private val historiaRepository: HistoriaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubirHistoriaState())
    val uiState: StateFlow<SubirHistoriaState> = _uiState

    fun subirHistoria(imageUri: Uri) {

        viewModelScope.launch {
            val usuarioActual = FirebaseAuth.getInstance().currentUser?.uid?: ""
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = historiaRepository.subirHistoria(usuarioActual, imageUri)

            if (result.isSuccess) {
                _uiState.update {
                    it.copy(isLoading = false, historiaSubida = true)
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = result.exceptionOrNull()?.message ?: "Error al subir historia"
                    )
                }
            }
        }
    }

    fun resetState() {
        _uiState.update { SubirHistoriaState() }
    }

    init{}
}