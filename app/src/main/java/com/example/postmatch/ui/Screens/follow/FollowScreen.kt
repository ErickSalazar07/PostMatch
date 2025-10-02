package com.example.postmatch.ui.Screens.follow

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.postmatch.R

import com.example.postmatch.data.PerfilInfo

import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.UsuarioInfo
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
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            FollowHeader()
        }
        item {
            ImagenFollow(state.usuario)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (perfilInfo.isFollowing) MaterialTheme.colorScheme.surfaceVariant // cuando ya lo sigues
                    else MaterialTheme.colorScheme.primary // cuando no lo sigues
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
            Divider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp)
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
        /*
        Image(
            painter = painterResource(id = R.drawable.ricardo_icon),
            contentDescription = stringResource(R.string.foto_de_perfil),
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp)), // Hacemos la imagen un poco más cuadrada
            contentScale = ContentScale.Crop
        )*/
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(resenha.usuarioFotoPerfil)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.user_icon),
            placeholder = painterResource(R.drawable.user_icon),
            contentDescription = stringResource(R.string.foto_de_perfil),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp)), // Hacemos la imagen un poco más cuadrada
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
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = resenha.descripcion,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .size(28.dp)
        )

        // Texto centrado
        Text(
            text = stringResource(R.string.perfil),
            color = MaterialTheme.colorScheme.onBackground,
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
        Text(stringResource(R.string.rese_as), color = MaterialTheme.colorScheme.onBackground, fontSize = 16.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Text(stringResource(R.string.guardado), color = MaterialTheme.colorScheme.onBackground, fontSize = 16.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Text(stringResource(R.string.m_s), color = MaterialTheme.colorScheme.onBackground, fontSize = 16.sp)
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
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.seguidores),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.width(24.dp))

        // Caja de Seguidos
        Box(
            modifier = Modifier
                .size(100.dp)
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = perfilInfo.seguidos.toString(),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.seguidos),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun ImagenFollow(
    usuarioInfo: UsuarioInfo,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen de perfil circular
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(usuarioInfo.fotoPerfil)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.user_icon),
            placeholder = painterResource(R.drawable.user_icon),
            contentDescription = stringResource(R.string.foto_de_perfil),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp).clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Nombre
        Text(
            text = perfilInfo.nombre,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        // Usuario
        Text(
            text = perfilInfo.arroba,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = perfilInfo.oficio,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
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