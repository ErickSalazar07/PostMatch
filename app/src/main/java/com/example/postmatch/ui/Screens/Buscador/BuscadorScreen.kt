package com.example.postmatch.ui.Screens.Buscador

import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.postmatch.R
import com.example.postmatch.data.local.LocalReviewProvider
import com.example.postmatch.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow



private val GreenAccent = Color(0xFF4CAF50)


@Composable
fun BuscadorScreenContent(
    viewModel: BuscarViewModel,
    modifier: Modifier = Modifier,
    onDestacadosClick: () -> Unit
){

    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(12.dp)
    ) {
        // Barra de búsqueda
        TextField(
            value = state.query,
            onValueChange = { viewModel.onBuscar(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        // Filtros
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            FiltroChip("Deporte")
            FiltroChip("Review")
            FiltroChip("Competición")
        }

        Spacer(Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            // 👇 Aquí no navegamos. Solo mostramos el filtro.
            FiltroChip(text = "Destacados",
                onClick = onDestacadosClick
            )


        }

        Spacer(Modifier.height(12.dp))

        // Lista de reseñas
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.reseñas) { reseña ->
                ReseñaCard(reseña)
            }
        }
    }
}

@Composable
fun FiltroChip(
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit = {}   // ✅ agregado callback
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable { onClick() }   // ✅ click

    ) {
        Text(
            text = text,
            color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ReseñaCard(reseña: Reseña) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen circular
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.size(56.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.estadio_bernabeu),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(reseña.titulo, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Por: ${reseña.autor}", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)

                Spacer(Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = reseña.rating.toString(),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Spacer(Modifier.width(6.dp))
                    repeat(5) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Text(
                    "${reseña.reviews} reviews",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 12.sp
                )

                Spacer(Modifier.height(4.dp))

                Text("Fecha: ${reseña.fecha}", color = Color.White, fontSize = 12.sp)
                Text("Equipos: ${reseña.equipos}", color = Color.White, fontSize = 12.sp)
                Text("Resumen: ${reseña.resumen}", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}


/*
// ✅ Preview
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BuscadorScreenPreview() {
    val fakeNavController = rememberNavController() // ✅ para que compile preview
    val fakeState = BuscarUIState(
        query = "",
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
                titulo = "Reseña del partido: Bayern vs. Dortmund",
                autor = "@Luis_Futbol",
                fecha = "15 de abril de 2024",
                equipos = "Bayern vs. Dortmund",
                resumen = "Análisis táctico y jugadas clave.",
                rating = 4.8,
                reviews = 98
            ),
            Reseña(
                id = 3,
                titulo = "Reseña del partido: Santa Fe vs. Dortmund",
                autor = "@Luis_Futbol",
                fecha = "15 de abril de 2024",
                equipos = "Santa Fe vs. Dortmund",
                resumen = "Análisis táctico y jugadas clave.",
                rating = 4.8,
                reviews = 98
            )
        )
    )


}
*/

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BuscadorScreenPreview() {
    val fakeState = BuscarUIState(
        query = "",
        reseñas = LocalReviewProvider.reviews.map { r ->
            Reseña(
                id = r.idReview,
                titulo = r.titulo,
                autor = r.usuarioNombre,
                fecha = r.fecha,
                equipos = r.partidoNombre,
                resumen = r.descripcion,
                rating = r.calificacion.toDouble(),
                reviews = r.numComentarios
            )
        }
    )


}
@Composable
fun BuscadorScreenContentPreview(state: BuscarUIState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(12.dp)
    ) {
        LazyColumn {
            items(state.reseñas, key = { it.id }) { reseña ->
                ReseñaCard(reseña)
            }
        }
    }
}


