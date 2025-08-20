package com.example.postmatch.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.postmatch.R

@Composable
fun RegistroScreen(
    modifier: Modifier = Modifier,
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fotoPerfil by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EncabezadoRegistro()

        Spacer(modifier = Modifier.height(60.dp))

        CampoTexto(
            label = stringResource(R.string.nombre),
            value = nombre,
            onValueChange = { nombre = it }
        )
        Spacer(modifier = Modifier.height(30.dp))

        CampoTexto(
            label = stringResource(R.string.email),
            value = email,
            onValueChange = { email = it }
        )
        Spacer(modifier = Modifier.height(30.dp))

        CampoTexto(
            label = stringResource(R.string.contrase_a),
            value = password,
            onValueChange = { password = it }
        )
        Spacer(modifier = Modifier.height(30.dp))

        CampoTexto(
            label = stringResource(R.string.foto_de_perfil_url),
            value = fotoPerfil,
            onValueChange = { fotoPerfil = it }
        )

        Spacer(modifier = Modifier.weight(1f))

        BotonRegistrar(
            onClick = {
                Log.d("RegistroScreen", "Datos ingresados:")
                Log.d("RegistroScreen", "Nombre: $nombre")
                Log.d("RegistroScreen", "Email: $email")
                Log.d("RegistroScreen", "Password: $password")
                Log.d("RegistroScreen", "FotoPerfil: $fotoPerfil")
            }
        )
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
                tint = Color.White
            )
            Text(
                text = stringResource(R.string.registro_de_usuario),
                fontWeight = FontWeight.Bold,
                color = Color.White,
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
            color = Color.White,
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
        label = { Text(label, color = Color.White) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent, RoundedCornerShape(32.dp)),
        visualTransformation = VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.verde),
            unfocusedContainerColor = colorResource(R.color.verde),
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
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
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.verde_claro)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "Registrar",
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RegistroScreenPreview() {
    RegistroScreen()
}
