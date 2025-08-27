package com.example.postmatch.ui.crearReview

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postmatch.R
import com.example.postmatch.data.PartidoInfo


@Composable
fun CrearReviewScreen(
    crearReviewViewModel: CrearReviewViewModel,
    modifier: Modifier = Modifier
) {
    val resenha by crearReviewViewModel.resenha.collectAsState()
    val partido by crearReviewViewModel.partido.collectAsState()
    val calificacion by crearReviewViewModel.calificacion.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.verde_oscuro))
            .padding(16.dp)
    ) {
        Encabezado()
        Spacer(modifier = Modifier.height(20.dp))
        MostrarPartidoCard(partido)
        Spacer(modifier = Modifier.height(30.dp))
        CalificacionInput(calificacion,onCalificacionChange = {crearReviewViewModel.updateCalificacion(it)})
        Spacer(modifier = Modifier.height(35.dp))
        ResenhaInput(resenha = resenha,onResenhaChange = { crearReviewViewModel.updateResenha(it)})
        Spacer(modifier = Modifier.weight(2f))
        BotonPublicar(
            onChange = { crearReviewViewModel.publicarButtonClick() }
        )
    }
}

@Composable
fun Encabezado(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(R.string.cerrar),
            tint = Color.White
        )
        Text(
            text = stringResource(R.string.escribir_rese_a),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.width(24.dp))
    }
}

@Composable
fun MostrarPartidoCard(
    partido: PartidoInfo,
    modifier: Modifier = Modifier
) {
    Column {
        Spacer(modifier = modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = partido.fecha,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = partido.nombre,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    "Santiago Bernabéu",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(25.dp))
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.verde)
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("VS", color = Color.White)
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            Image(
                painter = painterResource(id = R.drawable.real_madrid_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorResource(R.color.color_piel)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun CalificacionInput(
    calificacion: Int,
    onCalificacionChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val MAX_CALIFICACION: Int = 5
    var i:Int = 1

    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.calificaci_n),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            while(i <= MAX_CALIFICACION) {
                ReviewCalificacionButton(
                    calificacion = calificacion,
                    valorCalificacion = i,
                    onCalificacionChange = onCalificacionChange
                )
                if(0 < i && i < MAX_CALIFICACION)
                    Spacer(Modifier.width(5.dp))
                i++
            }
        }
    }
}

@Composable
fun ReviewCalificacionButton(
    calificacion: Int,
    valorCalificacion: Int,
    onCalificacionChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    IconButton(
        onClick = { onCalificacionChange(valorCalificacion)},
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = stringResource(R.string.calificaci_n),
            tint = if (calificacion >= valorCalificacion) Color.Yellow else Color.Gray,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun ResenhaInput(
    modifier: Modifier = Modifier,
    resenha: String,
    onResenhaChange: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.resenha),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF2A322B))
        ) {
            TextField(
                value = resenha,
                onValueChange = onResenhaChange,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(R.color.verde_oscuro2),
                    unfocusedContainerColor = colorResource(R.color.verde_oscuro2),
                    disabledContainerColor = colorResource(R.color.verde_oscuro2),
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
        }
    }
}

@Composable
fun BotonPublicar(
    onChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onChange,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.verde_claro)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = stringResource(R.string.publicar),
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CrearReviewScreenPreview() {
    CrearReviewScreen(
        crearReviewViewModel = viewModel()
    )
}
