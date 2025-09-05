package com.example.postmatch.ui.registro

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
import com.example.postmatch.R

@Composable
fun RegistroScreen(
    registroViewModel: RegistroViewModel,
    modifier: Modifier = Modifier,
) {
    val state by registroViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EncabezadoRegistro()

        Spacer(modifier = Modifier.height(60.dp))

        CampoTexto(
            label = stringResource(R.string.nombre),
            value = state.nombre,
            onValueChange = registroViewModel::updateNombre
        )
        Spacer(modifier = Modifier.height(30.dp))

        CampoTexto(
            label = stringResource(R.string.email),
            value = state.email,
            onValueChange = registroViewModel::updateEmail
        )
        Spacer(modifier = Modifier.height(30.dp))

        CampoTexto(
            label = stringResource(R.string.contrase_a),
            value = state.password,
            onValueChange = registroViewModel::updatePassword
        )
        Spacer(modifier = Modifier.height(30.dp))

        CampoTexto(
            label = stringResource(R.string.foto_de_perfil_url),
            value = state.urlFotoPerfil,
            onValueChange = registroViewModel::updateUrlFotoPerfil
        )

        Spacer(modifier = Modifier.weight(1f))
        BotonRegistrar( onClick = registroViewModel::regitrarButtonClick)
    }
}

@Composable
fun EncabezadoRegistro(
    modifier: Modifier = Modifier
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
                text = stringResource(R.string.registro_de_usuario),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.width(24.dp)) // para balance visual
        }
        Spacer(modifier = Modifier.height(25.dp))
        Image(
            painter = painterResource(R.drawable.logo_postmatch),
            contentDescription = stringResource(R.string.logo_postmatch),
            modifier = Modifier
                .size(125.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.Transparent),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(R.string.empieza_a_postear),
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
fun BotonRegistrar(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "Registrar",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RegistroScreenPreview() {
    RegistroScreen(
        registroViewModel = viewModel()
    )
}
