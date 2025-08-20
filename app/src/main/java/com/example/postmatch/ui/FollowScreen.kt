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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.postmatch.R

data class ReseniaFollowData(val idFoto: Int, val partido:String, val descripcion:String)

@Composable
fun FollowScreen(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.verde_oscuro))
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FollowHeader()
        ImagenFollow(fotoPerfil = R.drawable.ricardo_icon, nombrePerfil = "Ricardo", arrobaPerfil = "@Ricardo420", oficioPerfil = "Fanático madrid")

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.verde_claro)
            )
        ) {
            Text("Seguir")
        }

        InformacionFollow(102, 45324)

        MenuFollowOpciones()

        SeccionReseniasFollow(
            listaReseniasFollow = listOf(
                ReseniaFollowData(
                    idFoto = R.drawable.user_icon,
                    partido = "Real Madrid vs Barcelona",
                    descripcion = "Partidazo con mucha emoción, remontada épica."
                ),
                ReseniaFollowData(
                    idFoto = R.drawable.user_icon,
                    partido = "Liverpool vs Manchester City",
                    descripcion = "Juego muy parejo, lleno de ocasiones."
                ),
                ReseniaFollowData(
                    idFoto = R.drawable.user_icon,
                    partido = "Boca Juniors vs River Plate",
                    descripcion = "Ambiente increíble, goles y tensión hasta el final."
                ),
                ReseniaFollowData(
                    idFoto = R.drawable.user_icon,
                    partido = "PSG vs Bayern Múnich",
                    descripcion = "Velocidad, técnica y un final inesperado."
                ),
                ReseniaFollowData(
                    idFoto = R.drawable.user_icon,
                    partido = "Atlético de Madrid vs Sevilla",
                    descripcion = "Defensas sólidas, pocas ocasiones claras."
                ),
                ReseniaFollowData(
                    idFoto = R.drawable.user_icon,
                    partido = "Chelsea vs Arsenal",
                    descripcion = "Derbi londinense con mucha intensidad."
                ),
                ReseniaFollowData(
                    idFoto = R.drawable.user_icon,
                    partido = "Colombia vs Brasil",
                    descripcion = "Orgullo nacional, mucha garra y corazón."
                ),
                ReseniaFollowData(
                    idFoto = R.drawable.user_icon,
                    partido = "Milan vs Inter",
                    descripcion = "Un clásico que no decepcionó."
                ),
                ReseniaFollowData(
                    idFoto = R.drawable.user_icon,
                    partido = "Juventus vs Napoli",
                    descripcion = "Gran despliegue táctico de ambos lados."
                ),
                ReseniaFollowData(
                    idFoto = R.drawable.user_icon,
                    partido = "River Plate vs San Lorenzo",
                    descripcion = "Goles, polémicas y pasión hasta el final."
                )
            )
        )

    }
}

@Composable
fun SeccionReseniasFollow(
    modifier: Modifier = Modifier,
    listaReseniasFollow: List<ReseniaFollowData> // Define el tipo correctamente aquí
) {
    // Usamos LazyColumn para listas dinámicas y de mayor rendimiento
    LazyColumn(
        modifier = modifier
            .background(colorResource(id = R.color.verde_oscuro))
            .padding(vertical = 8.dp)
    ) {
        items(listaReseniasFollow) { reseniaFollow -> // Usamos 'items' para iterar sobre la lista
            ItemReseniaFollow(reseniaFollow) // Componente que recibe cada notificación
        }
    }
}

@Composable
fun ItemReseniaFollow(
    reseniaFollow: ReseniaFollowData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Foto de perfil
        Image(
            painter = painterResource(id = reseniaFollow.idFoto),
            contentDescription = stringResource(R.string.foto_de_perfil),
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Texto partido y descripción
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = reseniaFollow.partido,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = reseniaFollow.descripcion,
                fontSize = 14.sp,
                color = Color.LightGray
            )
        }
    }
}


@Composable
fun FollowHeader(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Icono de volver a la izquierda
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(R.string.volver),
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.CenterStart)
        )

        // Texto centrado
        Text(
            text = stringResource(R.string.perfil),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun MenuFollowOpciones() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(R.string.rese_as), color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Text(stringResource(R.string.guardado), color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Text(stringResource(R.string.m_s), color = Color.White, fontSize = 16.sp)
    }
}

@Composable
fun InformacionFollow(
    seguidores: Int,
    seguidos: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Caja de Seguidores
        Box(
            modifier = Modifier
                .size(100.dp)
                .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = seguidores.toString(),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.seguidores),
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.width(24.dp))

        // Caja de Seguidos
        Box(
            modifier = Modifier
                .size(100.dp)
                .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = seguidos.toString(),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.seguidos),
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun ImagenFollow(
    fotoPerfil: Int,
    nombrePerfil: String,
    arrobaPerfil: String,
    oficioPerfil: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.verde_oscuro))
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen de perfil circular
        Image(
            painter = painterResource(id = fotoPerfil), // pon aquí tu imagen de perfil mock
            contentDescription = stringResource(R.string.foto_de_perfil),
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.White), // fondo blanco por si la imagen no llena el círculo
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Nombre
        Text(
            text = nombrePerfil,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )

        // Usuario
        Text(
            text = arrobaPerfil,
            fontSize = 14.sp,
            color = Color.LightGray
        )
        Text(
            text = oficioPerfil,
            fontSize = 14.sp,
            color = Color.LightGray
        )
    }
}

@Composable
@Preview(showBackground = true)
fun FollowScreenPreview(){
    FollowScreen()
}