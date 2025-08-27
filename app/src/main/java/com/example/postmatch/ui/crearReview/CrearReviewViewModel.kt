package com.example.postmatch.ui.crearReview

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.local.LocalPartidoProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CrearReviewViewModel: ViewModel() {
    private val _resenha = MutableStateFlow("")
    val resenha: StateFlow<String> = _resenha

    private val _partido = MutableStateFlow(LocalPartidoProvider.partidos[0])
    val partido: StateFlow<PartidoInfo> = _partido

    private val _calificacion = MutableStateFlow(1)
    val calificacion: StateFlow<Int> = _calificacion

    val publicarButtonClick = MutableStateFlow({})

    fun updateResenha(input: String) {
        Log.d("CrearReviewViewModel", "updateResenha: $input")
        _resenha.value = input
    }

    fun updateCalificacion(input: Int) {
        Log.d("CrearReviewViewModel", "updateCalificacion: $input")
        _calificacion.value = input
    }

    fun setPublicarButtonClick(action: () -> Unit) {
        publicarButtonClick.value = action
    }

    fun publicarButtonClick() {
        Log.d("CrearReviewViewModel", "publicarButtonClick")
        publicarButtonClick.value()
    }

}