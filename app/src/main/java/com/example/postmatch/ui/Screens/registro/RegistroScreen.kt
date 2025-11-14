package com.example.postmatch.ui.Screens.registro

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postmatch.R

@Composable
fun RegistroScreen(
    registroViewModel: RegistroViewModel,
    onSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by registroViewModel.uiState.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {

        // 🔥 MISMO FONDO QUE LOGIN
        Image(
            painter = painterResource(R.drawable.fondo_login),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(1f))

            FormularioRegistro(
                nombre = state.nombre,
                email = state.email,
                password = state.password,
                fotoPerfil = state.urlFotoPerfil,
                onNombreChange = registroViewModel::updateNombre,
                onEmailChange = registroViewModel::updateEmail,
                onPasswordChange = registroViewModel::updatePassword,
                onFotoChange = registroViewModel::updateUrlFotoPerfil
            )

            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            BotonRegistrar(onClick = {
                registroViewModel.register()
                onSuccess()
            })

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun FormularioRegistro(
    nombre: String,
    email: String,
    password: String,
    fotoPerfil: String,
    onNombreChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onFotoChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {

        CampoInput(
            label = "Nombre",
            value = nombre,
            onValueChange = onNombreChange
        )

        CampoInput(
            label = "Email",
            value = email,
            onValueChange = onEmailChange
        )

        CampoInputPassword(
            label = "Contraseña",
            value = password,
            onValueChange = onPasswordChange
        )

        CampoInput(
            label = "Foto de perfil (URL)",
            value = fotoPerfil,
            onValueChange = onFotoChange
        )
    }
}

@Composable
fun CampoInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            cursorColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CampoInputPassword(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        },
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { visible = !visible }) {
                Icon(
                    painter = painterResource(
                        if (visible) R.drawable.eye_password_icon_show
                        else R.drawable.eye_password_icon_hide
                    ),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            cursorColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun BotonRegistrar(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .height(50.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text("Sign Up", color = MaterialTheme.colorScheme.onSecondary)
    }
}


@Composable
@Preview(showBackground = true)
fun RegistroScreenPreview() {
    RegistroScreen(
        registroViewModel = viewModel(),
        onSuccess = {}
    )
}
