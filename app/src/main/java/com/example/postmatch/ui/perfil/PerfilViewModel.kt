package com.example.postmatch.ui.perfil

import androidx.lifecycle.ViewModel
import com.example.postmatch.R
import com.example.postmatch.ui.partidos.PartidosState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// PerfilViewModel.kt
class PerfilViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilState())
    val uiState: StateFlow<PerfilState> = _uiState

    init {
        cargarPerfil()
    }

    private fun cargarPerfil() {
        // Aquí simulas datos mock (luego puede venir de BD o API)
        val reseniasMock = listOf(
            ReseniaPerfilData(5, "Excelente jugador", "Ricardo es muy disciplinado", R.drawable.ricardo_icon),
            ReseniaPerfilData(4, "Buen amigo", "Siempre está dispuesto a ayudar", R.drawable.ricardo_icon),
        )

        _uiState.value = PerfilState(
            nombre = "Ricardo",
            arroba = "@Ricardo_420",
            oficio = "Futbolista",
            seguidores = 1002,
            seguidos = 1293,
            fotoPerfil = R.drawable.ricardo_icon,
            listaResenias = reseniasMock
        )
    }
}
