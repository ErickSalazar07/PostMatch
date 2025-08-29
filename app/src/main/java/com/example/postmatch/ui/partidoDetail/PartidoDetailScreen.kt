package com.example.postmatch.ui.partidoDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Icon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postmatch.R
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.ReviewInfo


@Composable
 fun PartidoDetailScreen(
    partidoDetailViewModel: PartidoDetailViewModel,
    modifier: Modifier = Modifier
 ) {

     val state by partidoDetailViewModel.uiState.collectAsState()
     Column (
         modifier = modifier
             .fillMaxSize()
             .background(colorResource(id = R.color.verde_oscuro))
     ) {
         LazyColumn(
             modifier = Modifier
                 .padding(10.dp)
                 .fillMaxSize()
                 .background(colorResource(id = R.color.verde_oscuro))
         ) {

             item { AnalisisPartidoHeader(state.partido) }
             item {
                 Text(
                     text = stringResource(R.string.rese_as),
                     style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                     color = Color.White,
                     modifier = Modifier.padding(start = 8.dp, top = 24.dp, bottom = 8.dp)
                 )
             }

             items(
                 count = state.resenias.size,
             ) { index ->
                 ItemReseniaAnalisisPartido(state.resenias[index])
                 Spacer(modifier = Modifier.height(8.dp))
             }
         }
     }
 }

@Composable
fun AnalisisPartidoHeader(
    partido: PartidoInfo,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.partido),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.estadio_bernabeu),
            contentDescription = stringResource(R.string.foto_partido),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()              // ocupa todo el ancho
                .sizeIn(maxHeight = 200.dp)  // altura máxima
                .clip(shape = RoundedCornerShape(8.dp))
                .border(1.dp, Color.White, shape = RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.datos_del_partido),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
            modifier = Modifier.padding(start = 8.dp, bottom = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DatosEquipo(partido = partido, esLocal = true, modifier = Modifier.weight(1f))
            DatosEquipo(partido = partido, esLocal = false, modifier = Modifier.weight(1f))
        }
    }

}

@Composable
fun DatosEquipo(
    partido: PartidoInfo,
    esLocal: Boolean,
    modifier: Modifier = Modifier
) {
    val nombreEquipo = if (esLocal) partido.local else partido.visitante
    val goles = if (esLocal) partido.golesLocal else partido.golesVisitante
    val posesion = if (esLocal) partido.posesionLocal else partido.posesionVisitante
    val tiros = if (esLocal) partido.tirosLocal else partido.tirosVisitante
    val tagText = if (esLocal) stringResource(R.string.local) else stringResource(R.string.visitante)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(id = R.color.verde_oscuro2))
            .border(1.dp, Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = nombreEquipo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorResource(id = R.color.verde_oscuro))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = tagText,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }

            DatoEstadistica(label = stringResource(R.string.goles), value = goles.toString())
            DatoEstadistica(label = stringResource(R.string.posesi_n), value = "$posesion%")
            DatoEstadistica(label = stringResource(R.string.tiros), value = tiros.toString())
        }
    }
}

@Composable
fun DatoEstadistica(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.LightGray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}



 @Composable
 fun ItemReseniaAnalisisPartido(
     resenia: ReviewInfo,
     modifier: Modifier = Modifier
 ) {

     Column(
         modifier = modifier
             .fillMaxWidth()
             .padding(8.dp)
     ) {
         // ---------- SECCIÓN SUPERIOR ----------
         Row(
             verticalAlignment = Alignment.CenterVertically
         ) {
             // Foto del reseñador
             Image(
                 painter = painterResource(id = R.drawable.ricardo_icon),
                 contentDescription = "Foto de ${resenia.usuarioNombre}",
                 modifier = Modifier
                     .size(48.dp)
                     .clip(CircleShape)
                     .border(1.dp, Color.Gray, CircleShape),
                 contentScale = ContentScale.Crop
             )

             Spacer(modifier = Modifier.fillMaxWidth(0.02f))

             Column {
                 Text(
                     text = resenia.usuarioNombre,
                     style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                     color = Color.White
                 )

                 Row(verticalAlignment = Alignment.CenterVertically) {
                     repeat(resenia.calificacion) {
                         Icon(
                             imageVector = Icons.Default.Star,
                             contentDescription = "Estrella",
                             tint = Color.White,
                             modifier = Modifier.size(16.dp)
                         )
                     }
                 }
             }
         }

         Spacer(modifier = Modifier.height(8.dp))

         // Texto de la reseña
         Text(
             text = resenia.descripcion,
             style = MaterialTheme.typography.bodyMedium,
             modifier = Modifier.fillMaxWidth(),
             color = Color.White
         )

         Spacer(modifier = Modifier.height(8.dp))

         // ---------- SECCIÓN DE LIKES ----------
         Row(
             verticalAlignment = Alignment.CenterVertically
         ) {
             Icon(
                 imageVector = Icons.Default.ThumbUp,
                 contentDescription = stringResource(R.string.likes),
                 tint = Color.Gray,
                 modifier = Modifier
                     .size(20.dp)
             )
             Spacer(modifier = Modifier.fillMaxWidth(0.01f))
             Text(text = resenia.numLikes.toString(), color = Color.White)
         }
     }
 }


@Composable
@Preview(showBackground = true)
fun PartidoDetailScreenReview(){
    PartidoDetailScreen(
        partidoDetailViewModel = viewModel()
    )
}