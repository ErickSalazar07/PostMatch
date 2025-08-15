package com.example.postmatch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.postmatch.R



data class ReseniaAnalisisPartido(val fotoPerfil:Int, val nSemanas:Int, val nombreReseniador:String, val nEstrellas: Int, val nLikes:Int, val resenia:String)

@Composable
fun AnalisisPartidoScreen()
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.verde_oscuro))
    ){
        AnalisisPartidoHeader()
    }
}

@Composable
fun AnalisisPartidoHeader(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = stringResource(R.string.settings),
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.CenterEnd)
        )

        Text(
            text = stringResource(R.string.partido),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.Center)
        )

    }
    Text(
        text = "Resumen del partido 3-1",
        color = Color.LightGray,
        fontSize = 10.sp,
        modifier = Modifier
            .fillMaxWidth()         // ocupa todo el ancho
            .padding(start = 20.dp) // deja 20dp desde la izquierda

    )
    Spacer(modifier = Modifier.height(16.dp))
    Image(
        painter = painterResource(id = R.drawable.estadio_bernabeu),
        contentDescription = "Foto partido",
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
                textoAbajo = "2 - 1",
                modifier = Modifier.weight(1f)
            )
            RecuadroConTextos(
                textoArriba = stringResource(R.string.posesi_n),
                textoAbajo = "48% - 52%",
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
                textoAbajo = "15 - 10",
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
    SeccionReseniasAnalisisPartido(
        listaReseniasAnalisisPartido = listOf(
            ReseniaAnalisisPartido(R.drawable.ricardo_icon, 1, "Juan Pérez", 5, 120, "Excelente análisis, muy detallado y fácil de entender."),
            ReseniaAnalisisPartido(R.drawable.ricardo_icon, 2, "María López", 4, 85, "Muy bueno, aunque podría ser más conciso."),
            ReseniaAnalisisPartido(R.drawable.ricardo_icon, 3, "Carlos Gómez", 3, 40, "Interesante, pero faltaron algunos detalles del partido."),
            ReseniaAnalisisPartido(R.drawable.ricardo_icon, 4, "Lucía Fernández", 5, 200, "Me encantó la forma en que se explicó todo."),
            ReseniaAnalisisPartido(R.drawable.ricardo_icon, 5, "Pedro Martínez", 2, 15, "Poca información y poco análisis."),
            ReseniaAnalisisPartido(R.drawable.ricardo_icon, 6, "Ana Rodríguez", 4, 95, "Buen trabajo, volveré a leer sus reseñas.")
        )
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
            .padding(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(textoArriba, fontSize = 12.sp, color = Color.White)
            Text(textoAbajo, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun SeccionReseniasAnalisisPartido(
    modifier: Modifier = Modifier,
    listaReseniasAnalisisPartido: List<ReseniaAnalisisPartido> // Define el tipo correctamente aquí
) {
    // Usamos LazyColumn para listas dinámicas y de mayor rendimiento
    LazyColumn(
        modifier = modifier
            .background(colorResource(id = R.color.verde_oscuro))
            .padding(vertical = 8.dp)
    ) {
        items(listaReseniasAnalisisPartido) { reseniaAnalisisPartido -> // Usamos 'items' para iterar sobre la lista
            ItemReseniaAnalisisPartido(reseniaAnalisisPartido) // Componente que recibe cada notificación
        }
    }
}

@Composable
fun ItemReseniaAnalisisPartido(
    reseniaAnalisisPartido: ReseniaAnalisisPartido,
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
                painter = painterResource(id = reseniaAnalisisPartido.fotoPerfil),
                contentDescription = "Foto de ${reseniaAnalisisPartido.nombreReseniador}",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = reseniaAnalisisPartido.nombreReseniador,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(reseniaAnalisisPartido.nEstrellas) {
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

        Text(
            text = reseniaAnalisisPartido.resenia,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ThumbUp,
                contentDescription = stringResource(R.string.likes),
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = reseniaAnalisisPartido.nLikes.toString(), color = Color.White)

            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}


@Composable
@Preview(showBackground = true)
fun AnalisisPartidoScreenReview(){
    AnalisisPartidoScreen()
}