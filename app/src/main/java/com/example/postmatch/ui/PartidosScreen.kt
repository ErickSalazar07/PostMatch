package com.example.postmatch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.postmatch.data.local.LocalPartidoProvider
import androidx.compose.ui.tooling.preview.Preview

import com.example.postmatch.R



// ---------- CARD DE PARTIDO ----------
@Composable
fun PartidoCard(
    equipoLocal: String,
    equipoVisitante: String,
    marcador: String,
    eventos: List<String>,
    estadisticas: List<String>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(Color(0xFF1E1E1E)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Imagen del estadio
            Image(
                painter = painterResource(id = R.drawable.estadio_bernabeu),
                contentDescription = "Imagen del estadio",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 8.dp),
                contentScale = ContentScale.Crop
            )

            // Encabezado
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Equipo Local a la izquierda
                Text(
                    text = equipoLocal,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )

                // Marcador centrado
                Text(
                    text = marcador,
                    color = Color.Yellow,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                // Equipo Visitante a la derecha
                Text(
                    text = equipoVisitante,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
            }


            Spacer(modifier = Modifier.height(8.dp))

            // Eventos
            Text("Eventos importantes:", color = Color.Gray, fontWeight = FontWeight.SemiBold)
            eventos.forEach { evento ->
                Text("• $evento", color = Color.White, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Estadísticas
            Text("Estadísticas:", color = Color.Gray, fontWeight = FontWeight.SemiBold)
            estadisticas.forEach { stat ->
                Text("• $stat", color = Color.White, fontSize = 14.sp)
            }
        }
    }
}

// ---------- PANTALLA PRINCIPAL ----------
@Composable
fun PartidoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(8.dp)
    ) {
        Text(
            text = "Partidos Destacados",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )

        // Lista de partidos usando datos del provider
        LazyColumn {
            items(LocalPartidoProvider.partidos) { partido ->
                PartidoCard(
                    equipoLocal = partido.local,
                    equipoVisitante = partido.visitante,
                    marcador = "${partido.golesLocal} - ${partido.golesVisitante}",
                    eventos = listOf(
                        "Ejemplo de gol 1",
                        "Ejemplo de gol 2",
                        "Ejemplo tarjeta"
                    ),
                    estadisticas = listOf(
                        "Posesión: 50% - 50%",
                        "Tiros: 12 - 10",
                        "Faltas: 14 - 12",
                        "Corners: 6 - 4"
                    )
                )
            }
        }
    }
}

// ---------- PREVIEW ----------
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PartidoScreenPreview() {
    PartidoScreen()
}