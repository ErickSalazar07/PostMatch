package com.example.postmatch.ui.Screens.configuracionPerfil

import com.example.postmatch.data.SeccionConfiguracionInfo
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.local.LocalSeccionConfiguracionProvider
import com.example.postmatch.data.local.LocalUsuarioProvider

data class ConfiguracionPerfilState(
    val fotoPerfilUrl: String? = null,
    val usuario: UsuarioInfo = LocalUsuarioProvider.usuarios[0],
    val secciones: List<SeccionConfiguracionInfo> = emptyList(),
    val cerrarSesionButtonClick: () -> Unit = {}
)
