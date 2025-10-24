package com.example.postmatch.ui.Screens.Historia

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
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
    // Ejecutar la carga de historias al entrar a la pantalla
    LaunchedEffect(idUsuario) {
        historiaViewModel.getHistorias(idUsuario)
    }

    val state by historiaViewModel.uiState.collectAsState()

    // Mostrar historias (LazyRow para desplazarse horizontalmente)
    LazyRow(
        modifier = modifier.fillMaxSize()
    ) {
        items(state.historias) { historia ->
            HistoriaItem(historia = historia)
        }
    }
}

@Composable
fun HistoriaItem(historia: Historia) {
    Box(
        modifier = Modifier
            .fillMaxSize(),  // cada historia ocupa toda la pantalla
        contentAlignment = Alignment.TopStart
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
                .padding(start = 16.dp, top = 16.dp)
        )
    }
}