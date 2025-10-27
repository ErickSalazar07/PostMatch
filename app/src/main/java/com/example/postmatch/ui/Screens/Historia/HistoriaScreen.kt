package com.example.postmatch.ui.Screens.Historia

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.postmatch.data.Historia
import com.example.postmatch.ui.Screens.horasComoTexto
import kotlinx.coroutines.delay

@Composable
fun HistoriaScreen(
    historiaViewModel: HistoriaViewModel,
    idUsuario: String,
    modifier: Modifier = Modifier
) {
    Log.d("HistoriaScreen", "HistoriaScreen(Pre-llamado) - usuarioID: " + idUsuario)

    // Ejecutar la carga de historias al entrar a la pantalla
    LaunchedEffect(idUsuario) {
        historiaViewModel.getHistorias(idUsuario)
    }

    Log.d("HistoriaScreen", "HistoriaScreen(Post-llamado) - usuarioID: " + idUsuario)

    val state by historiaViewModel.uiState.collectAsState()

    Log.d("HistoriaScreen", "📊 Estado actual: $state")

    // Mostrar historias (LazyRow para desplazarse horizontalmente)
    LazyRow(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp), // Espacio entre historias
        contentPadding = PaddingValues(horizontal = 16.dp) // Padding lateral
    ) {
        items(state.historias) { historia ->
            HistoriaItem(
                historia = historia,
                modifier = Modifier
                    .fillMaxHeight() // Ocupa toda la altura
                    .width(300.dp)   // Ancho fijo para cada historia
            )
        }
    }
}

@Composable
fun HistoriaItem(
    historia: Historia,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp)) // Bordes redondeados
    ) {
        // Imagen de la historia
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(historia.imagenHistoria)
                .crossfade(true)
                .build(),
            contentDescription = "Historia",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Texto de las horas transcurridas
        Text(
            text = horasComoTexto(historia.horasHistoria),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}