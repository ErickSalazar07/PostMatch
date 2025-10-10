package com.example.postmatch.ui.Screens.perfil

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.postmatch.data.ReviewInfo


data class ReseniaPerfilData(val nEstrellas: Int, val tituloReseniaPerfil: String, val descripcionReseniaPerfil: String, val idFoto: Int)

//@Preview(showBackground = true)
@Composable
fun PerfilScreen(
    perfilViewModel: PerfilViewModel,
    configuracionButtonClick: () -> Unit,
    reviewButtonClick: () -> Unit,
    idPerfilUsuario: String,
    onReviewClick: (String) -> Unit,
    //idPartido: Int,
    modifier: Modifier = Modifier
){


    val state by perfilViewModel.uiState.collectAsState()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        item {
            PerfilHeader(onConfiguracionButtonClick = configuracionButtonClick,
                onReviewButtonClick = reviewButtonClick)
        }
        item {
            ImagenPerfil(
                fotoPerfilUrl = state.usuarioInfo.fotoPerfil,
                nombrePerfil = state.usuarioInfo.nombre,
                arrobaPerfil = state.usuarioInfo.email,
                onFotoPerfilButton = {},//perfilViewModel::uploadProfileImageToFirebase,
                oficioPerfil = "Futbolista")
        }
        item {
            InformacionCuenta(1002, 1293)
        }
        item {
            TextoIzquierda(stringResource(R.string.rese_as))
        }
        items(state.reviews) { resenhia ->
            ItemReseniaPerfil(
                reseniaPerfil = resenhia,
                onDeleteReview = { perfilViewModel.onDeleteReview(resenhia.idReview) }, // eliminar
                onClickReview = { idReview ->
                    // Acción para ver o seleccionar la reseña
                },
                onReviewClick = { idReview ->
                    // Acción para editar la reseña
                    onReviewClick(idReview) // callback que viene de PerfilScreen
                }
            )
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
    onDeleteReview: (String) -> Unit,
    onClickReview: (String) -> Unit,
    onReviewClick: (String) -> Unit,

    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Sección izquierda 70%
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
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Descripción
            Text(
                text = reseniaPerfil.descripcion,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Fila de botones (modificar y eliminar)
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onReviewClick(reseniaPerfil.idReview) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Modificar reseña",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(
                    onClick = { onDeleteReview(reseniaPerfil.idReview) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar reseña",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        // Sección derecha 30% (imagen)
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
            color = MaterialTheme.colorScheme.onBackground,
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
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = numFollow.toString(),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(idLabelFollow),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterStart),

                )
        }

        // Texto centrado
        Text(
            text = stringResource(R.string.perfil),
            color = MaterialTheme.colorScheme.onBackground,
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
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(28.dp)
            )
        }
    }
}


@Composable
fun ImagenPerfil(
    fotoPerfilUrl: String?,
    nombrePerfil: String,
    arrobaPerfil: String,
    oficioPerfil: String,
    onFotoPerfilButton: (uri:Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(fotoPerfilUrl)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.user_icon),
                placeholder = painterResource(R.drawable.user_icon),
                contentDescription = stringResource(R.string.foto_de_perfil),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp).clip(CircleShape)
            )

            PickImageButton(action = onFotoPerfilButton)

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre
            Text(
                text = nombrePerfil,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Usuario
            Text(
                text = arrobaPerfil,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = oficioPerfil,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun PickImageButton(
    action: (uri:Uri) -> Unit
) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            Log.d("PerfilScreen", uri.toString())
            action(uri)
        }
    }
    Button(
        onClick = {
            launcher.launch("image/*")
        }
    ) {
        Text(text = "Seleccionar Imagen")
    }
}
/*
@Composable
//@Preview(showBackground = true)
fun PerfilScreenPreview(
    modifier : Modifier = Modifier
){
    //ImagenPerfil(fotoPerfil = R.drawable.ricardo_icon, nombrePerfil = "Ricardito", arrobaPerfil = "@Ricardo_420", oficioPerfil = "Futbolista")
    PerfilScreen(
        configuracionButtonClick = {},
        reviewButtonClick = {},
        perfilViewModel = viewModel(),
        idPerfilUsuario = 1
    )
}


 */