package com.example.postmatch.ui.Screens.configuracionPerfil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.postmatch.R
import com.example.postmatch.data.OpcionConfiguracionButtonInfo
import com.example.postmatch.data.SeccionConfiguracionInfo
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.local.LocalSeccionConfiguracionProvider
import com.example.postmatch.data.local.LocalUsuarioProvider


@Composable
fun ConfiguracionPerfilScreen(
    configuracionPerfilViewModel: ConfiguracionPerfilViewModel,
    userId: String,
    modifier: Modifier = Modifier,
    onModificarPerfilClick: () -> Unit, // Acción al pulsar “Modificar perfil”
) {
    val state by configuracionPerfilViewModel.uiState.collectAsState()

    // 🔹 Cargar las secciones desde el provider local
    val secciones = remember {
        LocalSeccionConfiguracionProvider.seccionesConfiguracion
    }

    // 🔹 Reemplazar la acción de “Modificar perfil” con el callback real
    val seccionesActualizadas = secciones.map { seccion ->
        if (seccion.titulo == "Cuenta") {
            seccion.copy(
                opcionesConfiguracion = seccion.opcionesConfiguracion.map { opcion ->
                    if (opcion.titulo == "Modificar perfil") {
                        opcion.copy(onClick = onModificarPerfilClick)
                    } else opcion
                }
            )
        } else seccion
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Imagen de perfil y nombre
        item {
            ImagenPerfil(
                usuario = state.usuario,
                fotoPerfilUrl = state.fotoPerfilUrl
            )
        }

        // Secciones (Cuenta, Ayuda, etc.)
        items(seccionesActualizadas) { seccion ->
            SeccionConfiguracion(seccion = seccion)
        }

        // Botón de cerrar sesión
        item {
            Button(
                onClick = configuracionPerfilViewModel::cerrarSesionButtonClick,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    stringResource(R.string.cerrar_sesi_n),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}


@Composable
fun SeccionConfiguracion(
    seccion: SeccionConfiguracionInfo,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = seccion.titulo,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(16.dp)
        )
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(vertical = 8.dp)
        ) {

            for(opcion in seccion.opcionesConfiguracion) {
                ItemOpcion(opcion)
            }
        }
    }
}

@Composable
fun ItemOpcion(
    opcion: OpcionConfiguracionButtonInfo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { /* Acción */ }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = opcion.onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            // Icono en recuadro
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = opcion.idIcono),
                    contentDescription = opcion.titulo,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Título y subtítulo
            Column(modifier = Modifier.weight(1f)) {
                Text(opcion.titulo, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                Text(opcion.subtitulo, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
            }

            // Flecha
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = stringResource(R.string.ir),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun ImagenPerfil(
    usuario: UsuarioInfo,
    fotoPerfilUrl: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

       /*
        // Imagen de perfil circular
        Image(
            painter = painterResource(id = R.drawable.ricardo_icon), // pon aquí tu imagen de perfil mock
            contentDescription = stringResource(R.string.foto_de_perfil),
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface), // fondo blanco por si la imagen no llena el círculo
            contentScale = ContentScale.Crop
        )

        */
        AsyncImage(
            model = fotoPerfilUrl ?: R.drawable.ricardo_icon, // 👈 si no hay url, usa mock
            contentDescription = stringResource(R.string.foto_de_perfil),
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentScale = ContentScale.Crop
        )



        Spacer(modifier = Modifier.height(8.dp))

        // nombre
        Text(
            text = usuario.nombre,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        // correo
        Text(
            text = usuario.email,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ConfiguracionPerfilScreenPreview() {
    ConfiguracionPerfilScreen(
        configuracionPerfilViewModel = viewModel(),
        userId =  "",
        onModificarPerfilClick = {}
    )
}

