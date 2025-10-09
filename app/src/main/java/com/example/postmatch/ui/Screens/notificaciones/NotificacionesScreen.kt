package com.example.postmatch.ui.Screens.notificaciones

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.postmatch.R
import com.example.postmatch.data.UsuarioInfo

@Composable
fun NotificacionesScreen(
    notificacionesViewModel: NotificacionesViewModel,
    onNotificacionUsuarioClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by notificacionesViewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        NotificacionesHeader()
        SeccionNotificaciones(
            listaUsuariosNotificacion = state.usuariosNotificacion,
            onUsuarioNotificacionClick = onNotificacionUsuarioClick
        )
    }
}

@Composable
fun NotificacionesHeader(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Texto centrado
        Text(
            text = stringResource(R.string.notificaciones),
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
fun SeccionNotificaciones(
    modifier: Modifier = Modifier,
    onUsuarioNotificacionClick: (String) -> Unit,
    listaUsuariosNotificacion: List<UsuarioInfo> // Define el tipo correctamente aquí
) {
    // Usamos LazyColumn para listas dinámicas y de mayor rendimiento
    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 8.dp)
    ) {
        items(count = listaUsuariosNotificacion.size) { index -> // Usamos 'items' para iterar sobre la lista
            ItemNotificacion(
                usuarioNotificacion = listaUsuariosNotificacion[index],
                onUsuarioNotificacionClick = onUsuarioNotificacionClick
            ) // Componente que recibe cada notificación
        }
    }
}

@Composable
fun ItemNotificacion(
    usuarioNotificacion: UsuarioInfo,
    onUsuarioNotificacionClick: (String) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onUsuarioNotificacionClick(usuarioNotificacion.idUsuario) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically

    ){
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape),
            contentAlignment = Alignment.Center
        ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(usuarioNotificacion.fotoPerfil)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ricardo_icon),
                placeholder = painterResource(R.drawable.user_icon),
                contentDescription = stringResource(R.string.foto_de_perfil),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)){
            Text(text = "A ${usuarioNotificacion.nombre} le gustó tu reseña", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
            Text("hace 2 semanas", fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
@Preview//(showBackground= true)
fun NotificacionesScreenPreview(){
    NotificacionesScreen(
        notificacionesViewModel = viewModel(),
        onNotificacionUsuarioClick = {}
    )
}

