package com.example.postmatch.ui.Screens.perfil

import android.net.Uri
import android.util.Log
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


    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 🔹 Encabezado
        item {
            PerfilHeader(
                onConfiguracionButtonClick = configuracionButtonClick,
                onReviewButtonClick = reviewButtonClick
            )
        }

        // 🔹 Información del perfil
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                ImagenPerfil(
                    fotoPerfilUrl = state.fotoPerfilUrl,
                    nombrePerfil = state.usuarioInfo.nombre,
                    arrobaPerfil = state.usuarioInfo.email,
                    onFotoPerfilButton = perfilViewModel::uploadProfileImageToFirebase,
                    oficioPerfil = "Futbolista",
                    isCurrentUser = state.isCurrentUser,
                    idUsuario = idPerfilUsuario,
                    onClickHistoria = onClickHistoria ,
                    onClickSubirHistoria = onClickSubirHistoria,
                    historiaActiva = state.usuarioInfo.historiaActiva
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 🔹 Botón seguir (solo si no es el usuario actual)
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

        // 🔹 Estadísticas del perfil
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

        // 🔹 Título de reseñas
        item {
            TextoIzquierda(stringResource(R.string.rese_as))
        }

        // 🔹 Lista de reseñas
        items(state.reviews) { review ->
            ItemReseniaPerfil(
                reseniaPerfil = review,
                onDeleteReview = { perfilViewModel.onDeleteReview(review.idReview) },
                onClickReview = { /* acción ver reseña */ },
                onReviewClick = { onReviewClick(review.idReview) },
                isCurrentUser = state.isCurrentUser
            )
        }
    }

    if (mostrarDialogo.value) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo.value = false },
            title = {
                Text(if (mostrandoSeguidores.value) "Seguidores" else "Seguidos")
            },
            text = {
                val listaUsuarios = if (mostrandoSeguidores.value)
                    state.seguidoresList
                else
                    state.seguidosList

                if (listaUsuarios.isEmpty()) {
                    Text("No hay usuarios en esta lista.")
                } else {
                    LazyColumn {
                        items(listaUsuarios) { usuario ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                            ) {
                                AsyncImage(
                                    model = usuario.fotoPerfil,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(usuario.nombre, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { mostrarDialogo.value = false }) {
                    Text("Cerrar")
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
                if(isCurrentUser) {
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
    onClickSeguidores: () -> Unit,
    onClickSeguidos: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CajaInfoNumFollow(
            numFollow = seguidores,
            idLabelFollow = R.string.seguidores,
            modifier = Modifier.clickable { onClickSeguidores() }
        )
        Spacer(modifier = Modifier.width(24.dp))
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

/*
@Composable
fun ImagenPerfil(
    fotoPerfilUrl: String?,
    nombrePerfil: String,
    arrobaPerfil: String,
    oficioPerfil: String,
    isCurrentUser: Boolean,
    historiaActiva: Boolean, // añadimos este parámetro
    onFotoPerfilButton: (uri: Uri) -> Unit,
    onClickHistoria: (String) -> Unit,
    idUsuario: String,
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

            Box(
                modifier = Modifier.size(210.dp), // Espacio para el borde + foto
                contentAlignment = Alignment.Center
            ) {
                // 🔵 Borde degradado (solo si hay historia activa)
                if (historiaActiva) {
                    Box(
                        modifier = Modifier
                            .size(210.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF00FF99), // Verde brillante
                                        Color(0xFF00CC66)  // Verde más oscuro
                                    )
                                )
                            )
                    )
                }

                // 🟣 Imagen de perfil
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
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .then(
                            if (!historiaActiva)
                                Modifier.border(
                                    2.dp,
                                    MaterialTheme.colorScheme.outline,
                                    CircleShape
                                )
                            else Modifier
                        )
                )

                // 👁️ Ícono de ver historia
                if (historiaActiva) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "Ver historia",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = (-8).dp, y = 8.dp)
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0xAA000000))
                            .clickable { onClickHistoria(idUsuario) } //Aquí se ejecuta la función para navegar a historias
                    )
                }
            }

            // 📸 Botón de cambiar foto (solo si es el usuario actual)
            if (isCurrentUser) PickImageButton(action = onFotoPerfilButton)

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

            // Oficio
            Text(
                text = oficioPerfil,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
*/

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

            Box(
                modifier = Modifier.size(210.dp),
                contentAlignment = Alignment.Center
            ) {
                // 🔵 Borde degradado (solo si hay historia activa)
                if (historiaActiva) {
                    Box(
                        modifier = Modifier
                            .size(210.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF00FF99),
                                        Color(0xFF00CC66)
                                    )
                                )
                            )
                    )
                }

                // 🟣 Imagen de perfil
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
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .then(
                            if (!historiaActiva)
                                Modifier.border(
                                    2.dp,
                                    MaterialTheme.colorScheme.outline,
                                    CircleShape
                                )
                            else Modifier
                        )
                )

                // 👁️ Ícono de ver historia
                if (historiaActiva) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "Ver historia",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = (-8).dp, y = 8.dp)
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0xAA000000))
                            .clickable { onClickHistoria(idUsuario) }
                    )
                }

                // ➕ Botón de agregar historia (solo si es el perfil del usuario actual)
                if (isCurrentUser) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar historia",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = (-10).dp, y = (-10).dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable { onClickSubirHistoria() }
                    )
                }
            }

            // 📸 Botón de cambiar foto (solo si es el usuario actual)
            if (isCurrentUser) PickImageButton(action = onFotoPerfilButton)

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

            // Oficio
            Text(
                text = oficioPerfil,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
    val colorFondo = if (seguido) Color.Gray else MaterialTheme.colorScheme.primary

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorFondo
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .height(45.dp)
    ) {
        Text(
            text = textoBoton,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
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