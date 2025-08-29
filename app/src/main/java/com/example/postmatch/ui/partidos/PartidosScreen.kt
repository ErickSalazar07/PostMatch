package com.example.postmatch.ui.partidos

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.postmatch.data.local.LocalPartidoProvider
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.postmatch.R
import com.example.postmatch.data.PartidoInfo


// ---------- PANTALLA PRINCIPAL ----------
@Composable
fun PartidoScreen(
    partidoViewModel: PartidosViewModel,
    onPartidoClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val state by partidoViewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.verde_oscuro)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Partidos Destacados",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
        SectionPartidos(
            partidos = state.partidos,
            onPartidoClick = onPartidoClick
        )
    }
}

@Composable
fun SectionPartidos(
   partidos: List<PartidoInfo>,
   onPartidoClick: (Int) -> Unit,
   modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.verde_oscuro))
            .padding(8.dp)
    ) {
        items(count = partidos.size) { index -> // Lista de partidos usando datos del provider
            PartidoCard(
                partido = partidos[index],
                onPartidoClick = onPartidoClick
            )
        }
    }
}

// ---------- CARD DE PARTIDO ----------
@Composable
fun PartidoCard(
    partido: PartidoInfo,
    onPartidoClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onPartidoClick(partido.idPartido) },
        colors = CardDefaults.cardColors(Color(0xFF1E1E1E)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Imagen del estadio
            Image(
                painter = painterResource(id = R.drawable.estadio_bernabeu),
                contentDescription = "Imagen del estadio",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()              // ocupa todo el ancho
                    .sizeIn(maxHeight = 200.dp)  // altura máxima
                    .clip(shape = RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            ResultadoPartidoCard(partido = partido)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "${partido.categoria} ⚽",
                    color = Color.White,
                    fontSize = 15.sp
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
            color = Color.White,
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
            color = Color.White,
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
            color = Color.White,
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
        onPartidoClick = {}
    )
}