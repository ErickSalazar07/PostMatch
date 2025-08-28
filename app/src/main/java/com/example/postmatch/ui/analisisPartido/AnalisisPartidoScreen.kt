package com.example.postmatch.ui.analisisPartido

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postmatch.R
import com.example.postmatch.data.PartidoInfo
import com.example.postmatch.data.ReseniaAnalisisPartidoInfo
import com.example.postmatch.data.local.LocalAnalisisPartidoProvider


 @Composable
 fun AnalisisPartidoScreen(
     analisisPartidoViewModel: AnalisisPartidoViewModel,
     modifier: Modifier = Modifier
 ) {

     val state by analisisPartidoViewModel.uiState.collectAsState()
     LazyColumn(
         modifier = modifier
             .fillMaxSize()
             .background(colorResource(id = R.color.verde_oscuro))
     ) {
         item { AnalisisPartidoHeader(state.partido) }
         items(
             count = state.resenias.size,
         ) { index ->
             ItemReseniaAnalisisPartido(state.resenias[index])
             Spacer(modifier = Modifier.height(8.dp))
         }
     }
 }

@Composable
fun AnalisisPartidoHeader(
    partido: PartidoInfo,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.partido),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.Center)
        )

    }
    Text(
        text = stringResource(R.string.resumen_del_partido),
        color = Color.LightGray,
        fontSize = 10.sp,
        modifier = Modifier
            .fillMaxWidth()         // ocupa todo el ancho
            .padding(start = 20.dp) // deja 20dp desde la izquierda

    )
    Spacer(modifier = Modifier.height(16.dp))
    Image(
        painter = painterResource(id = R.drawable.estadio_bernabeu),
        contentDescription = stringResource(R.string.foto_partido),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()              // ocupa todo el ancho
            .sizeIn(maxHeight = 200.dp)  // altura máxima
    )

    Spacer(modifier = Modifier.height(16.dp))

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp) // espacio entre filas
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp), // espacio entre recuadros
            modifier = Modifier.fillMaxWidth()
        ) {
            RecuadroConTextos(
                textoArriba = stringResource(R.string.goles),
                textoAbajo = "${partido.golesLocal} - ${partido.golesVisitante}",
                modifier = Modifier.weight(1f)
            )
            RecuadroConTextos(
                textoArriba = stringResource(R.string.posesi_n),
                textoAbajo = "${partido.posesionLocal}% - ${partido.posesionVisitante}%",
                modifier = Modifier.weight(1f)
            )
        }

        // Segunda fila con 1 recuadro centrado
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            RecuadroConTextos(
                textoArriba = stringResource(R.string.tiros),
                textoAbajo = "${partido.tirosLocal} - ${partido.tirosVisitante}",
            )
        }
    }
    Text(
        text = stringResource(R.string.rese_as),
        color = Color.LightGray,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier
            .fillMaxWidth()         // ocupa todo el ancho
            .padding(start = 20.dp) // deja 20dp desde la izquierda

    )
}

@Composable
fun RecuadroConTextos(
    textoArriba: String,
    textoAbajo: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(textoArriba, fontSize = 12.sp, color = Color.White)
            Text(textoAbajo, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

 @Composable
 fun ItemReseniaAnalisisPartido(
     resenia: ReseniaAnalisisPartidoInfo,
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
                 painter = painterResource(id = resenia.fotoPerfil),
                 contentDescription = "Foto de ${resenia.nombreReseniador}",
                 modifier = Modifier
                     .size(48.dp)
                     .clip(CircleShape)
                     .border(1.dp, Color.Gray, CircleShape),
                 contentScale = ContentScale.Crop
             )

             Spacer(modifier = Modifier.width(8.dp))

             Column {
                 Text(
                     text = resenia.nombreReseniador,
                     style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                     color = Color.White
                 )

                 Row(verticalAlignment = Alignment.CenterVertically) {
                     repeat(resenia.nEstrellas) {
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
             text = resenia.resenia,
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
                 tint = if (resenia.isLiked) Color.Blue else Color.Gray,
                 modifier = Modifier
                     .size(20.dp)
             )
             Spacer(modifier = Modifier.width(4.dp))
             Text(text = resenia.nLikes.toString(), color = Color.White)
             Spacer(modifier = Modifier.width(16.dp))
         }
     }
 }


@Composable
@Preview(showBackground = true)
fun AnalisisPartidoScreenReview(){
    AnalisisPartidoScreen(
        analisisPartidoViewModel = viewModel()
    )
}