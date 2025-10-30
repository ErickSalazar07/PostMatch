package com.example.postmatch.ui.Screens.subirHistoria

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
@Composable
fun SubirHistoriaScreen(
    subirHistoriaViewModel: SubirHistoriaViewModel,
    onNavigateBack: () -> Unit
) {
    var imagenSeleccionada by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val state by subirHistoriaViewModel.uiState.collectAsState()

    // Launcher para la galería
    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imagenSeleccionada = uri
    }

    // Observar cuando se suba exitosamente
    LaunchedEffect(state.historiaSubida) {
        if (state.historiaSubida) {
            Toast.makeText(context, "Historia subida exitosamente", Toast.LENGTH_SHORT).show()
            subirHistoriaViewModel.resetState()
            onNavigateBack()
        }
    }

    // Mostrar errores
    LaunchedEffect(state.error) {
        state.error?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1a1a1a))
    ) {
        if (imagenSeleccionada == null) {
            // Vista inicial: botón para añadir imagen
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { galeriaLauncher.launch("image/*") },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir imagen",
                    tint = Color.White,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Añadir imagen",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        } else {
            // Vista con la imagen seleccionada
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // Imagen de la historia
                AsyncImage(
                    model = imagenSeleccionada,
                    contentDescription = "Imagen de historia",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1a1a1a)),
                    contentScale = ContentScale.Fit
                )

                // Botones inferiores
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Botón Cancelar
                    Button(
                        onClick = {
                            imagenSeleccionada = null
                        },
                        enabled = !state.isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        border = BorderStroke(1.dp, Color.White),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Botón Subir
                    Button(
                        onClick = {
                            imagenSeleccionada?.let { uri ->
                                subirHistoriaViewModel.subirHistoria( uri)
                            }
                        },
                        enabled = !state.isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0095F6)
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Subir",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Botón de cerrar
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cerrar",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}
