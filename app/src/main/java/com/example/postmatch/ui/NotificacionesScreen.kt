package com.example.postmatch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.postmatch.R
import com.example.postmatch.data.NotificacionInfo
import com.example.postmatch.data.local.LocalNotificacionProvider

@Composable
fun NotificacionesScreen(
    modifier: Modifier = Modifier
) {
    val notificaciones = LocalNotificacionProvider.notificaciones
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.verde_oscuro))
    ) {
        notificacionesHeader()

        SeccionNotificaciones(
            listaNotificaciones = notificaciones
        )

    }
}

@Composable
fun notificacionesHeader(
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
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.Center)
        )

        // Icono a la derecha
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = stringResource(R.string.settings),
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.CenterEnd)
        )
    }
}


@Composable
fun SeccionNotificaciones(
    modifier: Modifier = Modifier,
    listaNotificaciones: List<NotificacionInfo> // Define el tipo correctamente aquí
) {
    // Usamos LazyColumn para listas dinámicas y de mayor rendimiento
    LazyColumn(
        modifier = modifier
            .background(colorResource(id = R.color.verde_oscuro))
            .padding(vertical = 8.dp)
    ) {
        items(listaNotificaciones) { notificacion -> // Usamos 'items' para iterar sobre la lista
            itemNotificacion(notificacion) // Componente que recibe cada notificación
        }
    }
}

@Composable
//@Preview()
fun itemNotificacion(
    notificacionData: NotificacionInfo,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {/*accion*/ }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically

    ){
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.White, shape = CircleShape),
            contentAlignment = Alignment.Center
        ){
            Icon(
                painter = painterResource(id = notificacionData.idFotoPerfil),
                contentDescription = notificacionData.descripcion,
                tint = Color.Black,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)){
            Text(text = "A ${notificacionData.nombreUsuario} le gustó tu reseña", fontWeight = FontWeight.SemiBold, color = Color.White)
            Text("hace ${notificacionData.nSemanas} semanas", fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
@Preview//(showBackground= true)
fun NotificacionesScreenPreview(){
    NotificacionesScreen()
}

