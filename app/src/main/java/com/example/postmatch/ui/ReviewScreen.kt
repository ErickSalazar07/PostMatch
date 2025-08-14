package com.example.postmatch.ui

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.postmatch.R


@Composable
fun ReviewScreen() {
    var resenha by remember { mutableStateOf("") }
    var calificacion by remember { mutableStateOf(0)}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0F0D))
            .padding(16.dp)
    ) {
        Encabezado()
        Spacer(modifier = Modifier.height(20.dp))
        SeleccionarPartidoCard()
        Spacer(modifier = Modifier.height(30.dp))
        CalificacionRow()
        Spacer(modifier = Modifier.height(35.dp))
        ResenhaInput(
            resenha = resenha,
            onResenhaChange = {resenha = it},
        )
        Spacer(modifier = Modifier.weight(2f))
        BotonPublicar()
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
            contentDescription = "Cerrar",
            tint = Color.White
        )
        Text(
            text = "Escribir reseña",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.width(24.dp))
    }
}

@Composable
fun SeleccionarPartidoCard(
    modifier: Modifier = Modifier
) {
    Column {
        Spacer(modifier = modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0D0F0D)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("20 de mayo", color = Color.Gray, fontSize = 14.sp)
                Text(
                    "Real Madrid vs. FC Barcelona",
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
                        containerColor = Color(0xFF1E2E1F)
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
                    .background(Color(0xFFF5E1C5)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun CalificacionRow(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Calificación",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ReviewCalificacionButton()
            ReviewCalificacionButton()
            ReviewCalificacionButton()
            ReviewCalificacionButton()
            ReviewCalificacionButton()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewCalificacionButtonPreview() {
    ReviewCalificacionButton()
}

@Composable
fun ReviewCalificacionButton(
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(8.dp) // reutilizamos la misma forma

    Button(
        onClick = {},
        modifier = modifier
            .width(65.dp)
            .padding(horizontal = 1.dp)
            .border(1.dp, Color.White, shape), // borde con esquinas redondeadas
        shape = shape, // misma forma para el botón
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent, // fondo transparente
            contentColor = Color.White          // texto blanco
        )
    ) {
        Text(
            text = "⭐",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 12.sp
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
                )
            )
        }
    }
}

@Composable
fun BotonPublicar(
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {},
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DB954)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "Publicar",
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ReviewScreenPreview() {
    ReviewScreen()
}
