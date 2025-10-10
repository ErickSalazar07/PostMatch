package com.example.postmatch.ui.Screens.modificarPerfil
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.postmatch.R
import com.example.postmatch.ui.Screens.registro.RegistroState

@Composable
fun ModificarPerfilScreen(
    modificarPerfilViewModel: ModificarPerfilViewModel,
    modifier: Modifier = Modifier,
    onSuccess: () -> Unit,
    UserId: String,
) {
    val state by modificarPerfilViewModel.uiState.collectAsState()

   

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EncabezadoModificarPerfil(state = state)

        Spacer(modifier = Modifier.height(60.dp))

        CampoTexto(
            label = stringResource(R.string.nombre),
            value = state.nombre,
            onValueChange = modificarPerfilViewModel::updateNombre
        )
        Spacer(modifier = Modifier.height(30.dp))


        CampoTexto(
            label = stringResource(R.string.contrase_a),
            value = state.password,
            onValueChange = modificarPerfilViewModel::updatePassword
        )
        Spacer(modifier = Modifier.height(30.dp))

        CampoTexto(
            label = stringResource(R.string.foto_de_perfil_url),
            value = state.urlFotoPerfil,
            onValueChange = modificarPerfilViewModel::updateUrlFotoPerfil
        )

        if (state.errorMessage != null) {
            Text(
                text = state.errorMessage!!,
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        BotonGuardarCambios(
            onClick = { modificarPerfilViewModel.updateUser()

                onSuccess()
            } // usa tu método actual

        )
    }
}

@Composable
fun EncabezadoModificarPerfil(
    state: ModificarPerfilState,
    modifier: Modifier = Modifier,

    ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBackIosNew,
                contentDescription = "Volver",
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.email),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.width(24.dp)) // espacio para balance visual
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Muestra la foto actual o el logo si no hay URL
        val imagen = if (state.urlFotoPerfil.isNotEmpty()) {
            rememberAsyncImagePainter(state.urlFotoPerfil)
        } else {
            painterResource(R.drawable.logo_postmatch)
        }

        Image(
            painter = imagen,
            contentDescription = stringResource(R.string.foto_de_perfil),
            modifier = Modifier
                .size(125.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.Transparent),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(R.string.agregar_comentario),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun CampoTexto(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = MaterialTheme.colorScheme.onPrimary) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent, RoundedCornerShape(32.dp)),
        visualTransformation = VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.onPrimary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun BotonGuardarCambios(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "Guardar cambios",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ModificarPerfilScreenPreview() {
    ModificarPerfilScreen(
        modificarPerfilViewModel = viewModel(),
        UserId = "",
        onSuccess = {}
    )
}
