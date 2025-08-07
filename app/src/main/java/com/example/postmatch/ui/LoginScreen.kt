package com.example.postmatch.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.postmatch.R

@Composable
fun LoginBody(
    modifier: Modifier = Modifier
) {

}

@Composable
fun AppBoton(
    textoBoton: String,
    background: Color,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = background
        ),
        modifier = modifier
    ) {
        Text(text=textoBoton)
    }
}

@Composable
fun FormularioLogin(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(colorResource(R.color.verde_oscuro)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Correo") },
            modifier = Modifier
                .background(colorResource(R.color.verde))
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Password") },
            modifier = Modifier
                .background(colorResource(R.color.verde))
        )
        AppBoton("Log In",colorResource(R.color.verde_claro))
        AppBoton("Sign Up",colorResource(R.color.verde))
        Text(
            "By continuing, you agree to our Terms of Service and Privacy Policy",
            color = colorResource(R.color.white),
            fontSize = 17.sp,
        )
    }
}

@Composable
fun LoginScreen() {
    Column {
        FormularioLogin()
    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginScreen()
}