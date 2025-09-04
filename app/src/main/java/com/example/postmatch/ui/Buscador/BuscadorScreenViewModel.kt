package com.example.postmatch.ui.Buscador

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmatch.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class BuscarViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(BuscarUIState())
    val uiState: StateFlow<BuscarUIState> = _uiState

    fun onBuscar(query: String) {
        _uiState.value = _uiState.value.copy(
            query = query,
            isLoading = true
        )

        // Simulación de carga (esto normalmente sería una llamada a un repositorio/BD/API)
        viewModelScope.launch {
            delay(1000)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                reseñas = listOf(
                    Reseña(
                        id = 1,
                        titulo = "Reseña del partido: Real Madrid vs. Barcelona",
                        autor = "@Alex_Ramos",
                        fecha = "20 de mayo de 2024",
                        equipos = "Real Madrid vs. Barcelona",
                        resumen = "Reseñas de aficionados y expertos sobre el partido.",
                        rating = 4.5,
                        reviews = 120
                    ),
                    Reseña(
                        id = 2,
                        titulo = "Reseña del partido: Real Madrid vs. Barcelona",
                        autor = "@Alex_Ramos",
                        fecha = "20 de mayo de 2024",
                        equipos = "Real Madrid vs. Barcelona",
                        resumen = "Reseñas de aficionados y expertos sobre el partido.",
                        rating = 4.5,
                        reviews = 120
                    )
                )
            )
        }
    }
}
