package com.example.postmatch.ui.follow

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
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postmatch.R

import com.example.postmatch.data.PerfilInfo

import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalPerfilInfoData
import com.example.postmatch.data.local.LocalPerfilInfoData.perfilInfo


@Composable
fun FollowScreen(
    onFollowButtonChange:() -> Unit,
    followViewModel: FollowViewModel,
    modifier: Modifier = Modifier
){
    val state by followViewModel.uiState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.verde_oscuro))
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            FollowHeader()
        }
        item {
            ImagenFollow(perfilInfo)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (perfilInfo.isFollowing) Color.Gray // cuando ya lo sigues
                    else colorResource(R.color.verde_claro) // cuando no lo sigues
                )
            ) {
                Text(
                    if (perfilInfo.isFollowing) "Siguiendo" else "Seguir"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item { MenuFollowOpciones() }

        items(count = state.resenhas.size) { index ->
            ItemReseniaFollow(state.resenhas[index])
            Divider(color = Color.DarkGray, thickness = 1.dp)
        }
    }
}

@Composable
fun SeccionReseniasFollow(
    modifier: Modifier = Modifier,
    listaReseniasFollow: List<ReviewInfo> // Define el tipo correctamente aquí
) {
    // Usamos LazyColumn para listas dinámicas y de mayor rendimiento
    LazyColumn(
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        items(listaReseniasFollow) { reseniaFollow -> // Usamos 'items' para iterar sobre la lista
            ItemReseniaFollow(reseniaFollow) // Componente que recibe cada notificación
            Divider(color = Color.DarkGray, thickness = 1.dp)
        }    }
}

@Composable
fun ItemReseniaFollow(
    resenha: ReviewInfo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp), // Aumentamos el espacio vertical
        verticalAlignment = Alignment.Top
    ) {
        // Foto de perfil
        Image(
            painter = painterResource(id = R.drawable.ricardo_icon),
            contentDescription = stringResource(R.string.foto_de_perfil),
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp)), // Hacemos la imagen un poco más cuadrada
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Texto partido y descripción
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = resenha.partidoNombre,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = resenha.descripcion,
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
    Row(
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
        )

        // Texto centrado
        Text(
            text = stringResource(R.string.perfil),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
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
    perfilInfo: PerfilInfo,
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
                    text = perfilInfo.seguidores.toString(),
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
                    text = perfilInfo.seguidos.toString(),
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
    perfilInfo: PerfilInfo,
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
            painter = painterResource(id = perfilInfo.foto), // pon aquí tu imagen de perfil mock
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
            text = perfilInfo.nombre,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )

        // Usuario
        Text(
            text = perfilInfo.arroba,
            fontSize = 14.sp,
            color = Color.LightGray
        )
        Text(
            text = perfilInfo.oficio,
            fontSize = 12.sp,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(16.dp))
    }


    InformacionFollow(perfilInfo)
}

@Composable
@Preview(showBackground = true)
fun FollowScreenPreview(){
    FollowScreen(
        onFollowButtonChange = {},
        followViewModel = viewModel()
    )
}