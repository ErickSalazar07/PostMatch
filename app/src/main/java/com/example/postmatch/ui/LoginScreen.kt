package com.example.postmatch.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.postmatch.R


@Composable
fun HeaderLogin(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo_estadio),
            contentDescription = "Fondo Estadio",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .background(
                    color = colorResource(R.color.verde),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(8.dp)
                .width(200.dp)
                .height(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_postmatch_nobackg),
                contentDescription = "Logo PostMatch",
                modifier = Modifier
                    .width(200.dp)
                    .height(70.dp),
                contentScale = ContentScale.Fit
            )
        }

    }
}


@Composable
fun FormLogin(
    correo: String,
    usuario: String,
    password: String,
    onCorreoChange: (String) -> Unit,
    onUsuarioChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        FieldLogin(
            label = "Usuario",
            textDataField = usuario,
            onTextDataFieldChange = onUsuarioChange
        )

        FieldLogin(
            label = "Correo",
            textDataField = correo,
            onTextDataFieldChange = onCorreoChange
        )

        FieldLogin(
            label = "Password",
            textDataField = password,
            onTextDataFieldChange = onPasswordChange
        )
    }
}

@Composable
fun FieldLogin(
    label: String,
    textDataField: String,
    onTextDataFieldChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = textDataField,
        onValueChange = onTextDataFieldChange,
        label = { Text(text = label, color = Color.White) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.verde),
            unfocusedContainerColor = colorResource(R.color.verde),
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun BotonesLogin(
    onLogInChange: () -> Unit,
    onSignUpChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Button(
            onClick = onLogInChange,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.verde_claro)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log In")
        }

        Button(
            onClick = onSignUpChange,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.verde)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up", color = colorResource(R.color.white))
        }
    }
}

@Composable
fun TextoLegal(
    modifier: Modifier = Modifier
) {
    Text(
        text = "By continuing, you agree to our Terms of Service and Privacy Policy",
        color = colorResource(R.color.white),
        fontSize = 13.sp,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    )
}

@Composable
fun LoginScreen() {
    var usuario by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.fondo_login),
            contentDescription = "Fondo de pantalla login",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // para que no pegue tanto a los bordes
        ) {
            Spacer(modifier = Modifier.weight(1f))
            FormLogin(
                correo = correo,
                usuario = usuario,
                password = password,
                onCorreoChange = { correo = it },
                onUsuarioChange = { usuario = it },
                onPasswordChange = { password = it }
            )
            BotonesLogin(
                onLogInChange = {
                    Log.d("LoginScreen", "BOTON LOG IN desde el padre valores:")
                    Log.d("LoginScreen", "usuario: $usuario")
                    Log.d("LoginScreen", "correo: $correo")
                    Log.d("LoginScreen", "password: $password")
                },
                onSignUpChange = {
                    Log.d("LoginScreen", "BOTON SIGN UP desde el padre valores:")
                    Log.d("LoginScreen", "usuario: $usuario")
                    Log.d("LoginScreen", "correo: $correo")
                    Log.d("LoginScreen", "password: $password")
                }
            )
            Spacer(modifier = Modifier.height(25.dp))
            TextoLegal()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginScreen()
}