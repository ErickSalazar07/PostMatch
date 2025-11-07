package com.example.postmatch.ui.Screens.perfil

import androidx.compose.material3.AlertDialog
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.postmatch.R
import com.example.postmatch.data.ReviewInfo
import androidx.compose.ui.graphics.Brush
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun PerfilScreen(
    perfilViewModel: PerfilViewModel,
    configuracionButtonClick: () -> Unit,
    reviewButtonClick: () -> Unit,
    idPerfilUsuario: String,
    onReviewClick: (String) -> Unit,
    onClickHistoria: (String) -> Unit,
    onClickSubirHistoria: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        perfilViewModel.getUserInfo(idPerfilUsuario)
    }

    val state by perfilViewModel.uiState.collectAsState()
    val mostrarDialogo = remember { mutableStateOf(false) }
    val mostrandoSeguidores = remember { mutableStateOf(true) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                    )
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                PerfilHeader(
                    onConfiguracionButtonClick = configuracionButtonClick,
                    onReviewButtonClick = reviewButtonClick
                )
            }

            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    ImagenPerfil(
                        fotoPerfilUrl = state.fotoPerfilUrl,
                        nombrePerfil = state.usuarioInfo.nombre,
                        arrobaPerfil = state.usuarioInfo.email,
                        onFotoPerfilButton = perfilViewModel::uploadProfileImageToFirebase,
                        oficioPerfil = "Futbolista",
                        isCurrentUser = state.isCurrentUser,
                        idUsuario = idPerfilUsuario,
                        onClickHistoria = onClickHistoria,
                        onClickSubirHistoria = onClickSubirHistoria,
                        historiaActiva = state.usuarioInfo.historiaActiva
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    if (!state.isCurrentUser) {
                        SeguirButton(
                            seguido = state.usuarioInfo.followed,
                            onClick = {
                                perfilViewModel.seguirTantoDejarDeSeguirUsuario(idPerfilUsuario)
                            }
                        )
                    }
                }
            }

            item {
                InformacionCuenta(
                    seguidores = state.usuarioInfo.numFollowers,
                    seguidos = state.usuarioInfo.numFollowed,
                    onClickSeguidores = {
                        mostrandoSeguidores.value = true
                        mostrarDialogo.value = true
                        perfilViewModel.getSeguidoresYSeguidos(idPerfilUsuario)
                    },
                    onClickSeguidos = {
                        mostrandoSeguidores.value = false
                        mostrarDialogo.value = true
                        perfilViewModel.getSeguidoresYSeguidos(idPerfilUsuario)
                    }
                )
            }

            item {
                TextoIzquierda(stringResource(R.string.rese_as))
            }

            items(state.reviews) { review ->
                ItemReseniaPerfil(
                    reseniaPerfil = review,
                    onDeleteReview = { perfilViewModel.onDeleteReview(review.idReview) },
                    onClickReview = { },
                    onReviewClick = { onReviewClick(review.idReview) },
                    isCurrentUser = state.isCurrentUser
                )
            }
        }
    }

    if (mostrarDialogo.value) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo.value = false },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(24.dp),
            title = {
                Text(
                    text = if (mostrandoSeguidores.value) "Seguidores" else "Seguidos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    fontFamily = FontFamily.SansSerif
                )
            },
            text = {
                val listaUsuarios = if (mostrandoSeguidores.value)
                    state.seguidoresList
                else
                    state.seguidosList

                if (listaUsuarios.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay usuarios en esta lista.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 15.sp,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 400.dp)
                    ) {
                        items(listaUsuarios) { usuario ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                                    .padding(12.dp)
                            ) {
                                AsyncImage(
                                    model = usuario.fotoPerfil,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(14.dp))
                                Text(
                                    text = usuario.nombre,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.SansSerif
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { mostrarDialogo.value = false },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text(
                        text = "Cerrar",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                }
            }
        )
    }
}

@Composable
fun ItemReseniaPerfil(
    reseniaPerfil: ReviewInfo,
    onDeleteReview: (String) -> Unit,
    onClickReview: (String) -> Unit,
    onReviewClick: (String) -> Unit,
    isCurrentUser: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(0.65f)
                    .padding(end = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    repeat(reseniaPerfil.calificacion) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.estrella),
                            tint = Color(0xFFFFB800),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    repeat(5 - reseniaPerfil.calificacion) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.estrella),
                            tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Text(
                    text = reseniaPerfil.titulo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = FontFamily.SansSerif,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = reseniaPerfil.descripcion,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = FontFamily.SansSerif,
                    lineHeight = 20.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                if (isCurrentUser) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { onReviewClick(reseniaPerfil.idReview) },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Modificar reseña",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        IconButton(
                            onClick = { onDeleteReview(reseniaPerfil.idReview) },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar reseña",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(0.35f)
                    .aspectRatio(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ricardo_icon),
                    contentDescription = stringResource(R.string.imagen_rese_a),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )
            }
        }
    }
}

@Composable
fun TextoIzquierda(
    texto: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = texto,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    )
}

@Composable
fun CajaInfoNumFollow(
    numFollow: Int,
    idLabelFollow: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(140.dp)
            .height(90.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = numFollow.toString(),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(idLabelFollow),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}

@Composable
fun InformacionCuenta(
    seguidores: Int,
    seguidos: Int,
    onClickSeguidores: () -> Unit,
    onClickSeguidos: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CajaInfoNumFollow(
            numFollow = seguidores,
            idLabelFollow = R.string.seguidores,
            modifier = Modifier.clickable { onClickSeguidores() }
        )
        Spacer(modifier = Modifier.width(28.dp))
        CajaInfoNumFollow(
            numFollow = seguidos,
            idLabelFollow = R.string.seguidos,
            modifier = Modifier.clickable { onClickSeguidos() }
        )
    }
}

@Composable
fun PerfilHeader(
    onConfiguracionButtonClick: () -> Unit,
    onReviewButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            IconButton(
                onClick = onReviewButtonClick,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(44.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = stringResource(R.string.volver),
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = stringResource(R.string.perfil),
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.align(Alignment.Center)
            )

            IconButton(
                onClick = onConfiguracionButtonClick,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(44.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.settings),
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

@Composable
fun ImagenPerfil(
    fotoPerfilUrl: String?,
    nombrePerfil: String,
    arrobaPerfil: String,
    oficioPerfil: String,
    isCurrentUser: Boolean,
    historiaActiva: Boolean,
    onFotoPerfilButton: (uri: Uri) -> Unit,
    onClickHistoria: (String) -> Unit,
    onClickSubirHistoria: () -> Unit,
    idUsuario: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(180.dp),
            contentAlignment = Alignment.Center
        ) {
            if (historiaActiva) {
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF667eea),
                                    Color(0xFF764ba2),
                                    Color(0xFFf093fb)
                                )
                            )
                        )
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(fotoPerfilUrl)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.user_icon),
                placeholder = painterResource(R.drawable.user_icon),
                contentDescription = stringResource(R.string.foto_de_perfil),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(170.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .then(
                        if (!historiaActiva)
                            Modifier.border(
                                3.dp,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                CircleShape
                            )
                        else Modifier
                    )
            )

            if (historiaActiva) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 0.dp, y = 8.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF667eea),
                                    Color(0xFF764ba2)
                                )
                            )
                        )
                        .clickable { onClickHistoria(idUsuario) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "Ver historia",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            if (isCurrentUser) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 0.dp, y = 0.dp)
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable { onClickSubirHistoria() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar historia",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        if (isCurrentUser) {
            Spacer(modifier = Modifier.height(16.dp))
            PickImageButton(action = onFotoPerfilButton)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = nombrePerfil,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = FontFamily.SansSerif
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = arrobaPerfil,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontFamily = FontFamily.SansSerif
        )

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = oficioPerfil,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
fun SeguirButton(
    seguido: Boolean,
    onClick: () -> Unit
) {
    val textoBoton = if (seguido) "Siguiendo" else "Seguir"
    val colores = if (seguido) {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    } else {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        )
    }

    Button(
        onClick = onClick,
        colors = colores,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .height(50.dp)
            .widthIn(min = 160.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (seguido) 0.dp else 4.dp
        )
    ) {
        Text(
            text = textoBoton,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.SansSerif
        )
    }
}

@Composable
fun PickImageButton(
    action: (uri: Uri) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            Log.d("PerfilScreen", uri.toString())
            action(uri)
        }
    }

    OutlinedButton(
        onClick = { launcher.launch("image/*") },
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.height(46.dp)
    ) {
        Icon(
            imageVector = Icons.Default.PhotoCamera,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Cambiar Foto",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.SansSerif
        )
    }
}