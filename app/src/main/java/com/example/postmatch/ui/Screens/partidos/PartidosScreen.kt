package com.example.postmatch.ui.Screens.partidos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.postmatch.data.local.LocalPartidoProvider
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

import com.example.postmatch.R
import com.example.postmatch.data.PartidoInfo


// ---------- PANTALLA PRINCIPAL ----------
@Composable
fun PartidoScreen(
    partidoViewModel: PartidosViewModel,
    onReviewPartidoClick: (String) -> Unit,
    onPartidoClick: (String) -> Unit,  ///gggggg
    modifier: Modifier = Modifier
) {

    val state by partidoViewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Partidos Destacados",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(8.dp)
        )
        SectionPartidos(
            partidos = state.partidos,
            onPartidoClick = onPartidoClick,
            onReviewPartidoClick = onReviewPartidoClick
        )
    }
}

@Composable
fun SectionPartidos(
   partidos: List<PartidoInfo>,
   onPartidoClick: (String) -> Unit,
   onReviewPartidoClick: (String) -> Unit,
   modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        items(count = partidos.size) { index -> // Lista de partidos usando datos del provider
            PartidoCard(
                partido = partidos[index],
                onPartidoClick = onPartidoClick,
                onPartidoReviewClick = onReviewPartidoClick
            )
        }
    }
}

// ---------- CARD DE PARTIDO ----------
@Composable
fun PartidoCard(
    partido: PartidoInfo,
    onPartidoClick: (String) -> Unit,
    onPartidoReviewClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onPartidoClick(partido.idPartido) },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Imagen del estadio

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(partido.partidoFotoUrl)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.estadio_bernabeu),
                placeholder = painterResource(R.drawable.estadio_bernabeu),
                contentDescription = stringResource(R.string.foto_de_perfil),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            ResultadoPartidoCard(partido = partido)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "${partido.categoria} ⚽",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Botón para generar review
            Button(
                onClick = { onPartidoReviewClick(partido.idPartido) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Generar Review",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun ResultadoPartidoCard(
    partido: PartidoInfo,
    modifier: Modifier = Modifier
) {
    Row( // Encabezado
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        var colorLocal = Color.White
        var colorVisitante = Color.White

        if(partido.golesLocal > partido.golesVisitante) {
            colorLocal = Color.Green
            colorVisitante = Color.Red
        } else if(partido.golesLocal < partido.golesVisitante) {
            colorLocal = Color.Red
            colorVisitante = Color.Green
        } else {
            colorLocal = Color.Yellow
            colorVisitante = Color.Yellow
        }

        Text( // Equipo Local a la izquierda
            text = partido.local,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(3f),
            textAlign = TextAlign.Center
        )
        Text(
            text ="${partido.golesLocal}",
            color = colorLocal,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.5f),
            textAlign = TextAlign.End
        )
        Text( // Marcador centrado
            text = " - ",
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text ="${partido.golesVisitante}",
            color = colorVisitante,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.5f),
            textAlign = TextAlign.Start
        )
        Text( // Equipo Visitante a la derecha
            text = partido.visitante,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(3f),
            textAlign = TextAlign.Center
        )
    }
}

// ---------- PREVIEW ----------
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PartidoScreenPreview() {
    PartidoScreen(
        partidoViewModel = viewModel(),
        onPartidoClick = {},
        onReviewPartidoClick = {}
    )
}