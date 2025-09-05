package com.example.postmatch.ui.perfil

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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postmatch.R
import com.example.postmatch.data.ReviewInfo


data class ReseniaPerfilData(val nEstrellas: Int, val tituloReseniaPerfil: String, val descripcionReseniaPerfil: String, val idFoto: Int)

@Composable
fun PerfilScreen(
    perfilViewModel: PerfilViewModel,
    configuracionButtonClick: () -> Unit,
    reviewButtonClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val state by perfilViewModel.uiState.collectAsState()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.verde_oscuro))
    ){
        item {
            PerfilHeader(onConfiguracionButtonClick = configuracionButtonClick,
                onReviewButtonClick = reviewButtonClick)
        }
        item {
            ImagenPerfil(fotoPerfil = R.drawable.ricardo_icon, nombrePerfil = "Ricardo", arrobaPerfil = "@Ricardo_420", oficioPerfil = "Futbolista")
        }
        item {
            InformacionCuenta(1002, 1293)
        }
        item {
            TextoIzquierda(stringResource(R.string.rese_as))
        }
        items(state.resenhias) { resenhia -> // Usamos 'items' para iterar sobre la lista
            ItemReseniaPerfil(resenhia) // Componente que recibe cada notificación
        }
    }

}

@Composable
fun SeccionReseniasPerfil(
    modifier: Modifier = Modifier,
    resenhias: List<ReviewInfo> // Define el tipo correctamente aquí
) {
    // Usamos LazyColumn para listas dinámicas y de mayor rendimiento
    LazyColumn(
        modifier = modifier
            .background(colorResource(id = R.color.verde_oscuro))
            .padding(vertical = 8.dp)
    ) {
    }
}

@Composable
fun ItemReseniaPerfil(
    reseniaPerfil: ReviewInfo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(colorResource(id = R.color.verde_oscuro), shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Sección izquierda 70%
        Column(
            modifier = Modifier
                .weight(0.7f)
                .padding(end = 8.dp)
        ) {
            Row(
                modifier = modifier
                    .padding(bottom = 10.dp)
            ) {
                repeat(reseniaPerfil.calificacion) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = stringResource(R.string.estrella),
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            // Título
            Text(
                text = reseniaPerfil.titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
            // Descripción
            Text(
                text = reseniaPerfil.descripcion,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        //Sección derecha 30%
        Box(
            modifier = Modifier
                .weight(0.3f)
                .aspectRatio(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ricardo_icon),
                contentDescription = stringResource(R.string.imagen_rese_a),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}


@Composable
fun TextoIzquierda(
    texto: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = texto,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun CajaInfoNumFollow(
    numFollow: Int,
    idLabelFollow: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = numFollow.toString(),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(idLabelFollow),
                color = Color.White,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun InformacionCuenta(
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

        CajaInfoNumFollow(
            numFollow = seguidores,
            idLabelFollow = R.string.seguidores
        )
        Spacer(modifier = Modifier.width(24.dp))
        // Caja de Seguidos
        CajaInfoNumFollow(
            numFollow = seguidos,
            idLabelFollow = R.string.seguidos
        )
    }
}



@Composable
fun PerfilHeader(
    onConfiguracionButtonClick: () -> Unit,
    onReviewButtonClick:()->Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Icono de volver a la izquierda
        IconButton(
            onClick = onReviewButtonClick, // aquí va el click del back
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = stringResource(R.string.volver),
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterStart),

                )
        }

        // Texto centrado
        Text(
            text = stringResource(R.string.perfil),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.Center)
        )
        IconButton(
            onClick = onConfiguracionButtonClick,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(R.string.settings),
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
            )
        }
    }
}


@Composable
fun ImagenPerfil(
    fotoPerfil: Int,
    nombrePerfil: String,
    arrobaPerfil: String,
    oficioPerfil: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.verde_oscuro))
            .padding(vertical = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
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
}

@Composable
@Preview(showBackground = true)
fun PerfilScreenPreview(
    modifier : Modifier = Modifier
){
    //ImagenPerfil(fotoPerfil = R.drawable.ricardo_icon, nombrePerfil = "Ricardito", arrobaPerfil = "@Ricardo_420", oficioPerfil = "Futbolista")
    PerfilScreen(
        configuracionButtonClick = {},
        reviewButtonClick = {},
        perfilViewModel = viewModel()
    )
}