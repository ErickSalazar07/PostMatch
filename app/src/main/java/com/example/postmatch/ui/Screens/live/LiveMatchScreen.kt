package com.example.postmatch.ui.Screens.live

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.postmatch.data.PartidoInfo
import androidx.compose.ui.platform.LocalContext

@Composable
fun LiveMatchesScreen(viewModel: LiveMatchesViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 12.dp)
    ) {
        Text(
            text = "Partidos en Vivo",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            textAlign = TextAlign.Center
        )

        if (state.partidos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No hay partidos en vivo disponibles en este momento.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(state.partidos) { partido ->
                    LiveMatchCard(partido)
                }
            }
        }
    }
}

@Composable
fun LiveMatchCard(partido: PartidoInfo) {
    val context = LocalContext.current

    // Separamos los logos: liga;local;visitante
    val logos = partido.partidoFotoUrl.split(";")
    val leagueLogo = logos.getOrNull(0) ?: ""
    val homeLogo = logos.getOrNull(1) ?: ""
    val awayLogo = logos.getOrNull(2) ?: ""

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Liga o categoría
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(leagueLogo)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
                Text(
                    text = "${partido.nombre} • ${partido.categoria}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Equipos y marcador
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TeamColumn(
                    name = partido.local,
                    logoUrl = homeLogo,
                    alignEnd = false,
                    modifier = Modifier.weight(3f)
                )

                // Marcador
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(vertical = 10.dp, horizontal = 18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${partido.golesLocal}  -  ${partido.golesVisitante}",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                TeamColumn(
                    name = partido.visitante,
                    logoUrl = awayLogo,
                    alignEnd = true,
                    modifier = Modifier.weight(3f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "En juego",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun TeamColumn(
    name: String,
    logoUrl: String,
    alignEnd: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = if (alignEnd) Alignment.End else Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo arriba, centrado
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(logoUrl)
                .crossfade(true)
                .error(android.R.drawable.ic_menu_report_image) // placeholder si no carga
                .build(),
            contentDescription = "Logo del equipo",
            modifier = Modifier
                .size(48.dp)
                .align(if (alignEnd) Alignment.End else Alignment.Start)
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Nombre debajo
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            ),
            textAlign = if (alignEnd) TextAlign.End else TextAlign.Start,
            maxLines = 2
        )
    }
}


