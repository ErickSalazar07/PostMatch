package com.example.postmatch.ui.Screens.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postmatch.R

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier.testTag("loginScreen")
) {
    val state by loginViewModel.uiState.collectAsState()


    Box(
        modifier = modifier
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
                correo = state.correo,
                usuario = state.usuario,
                password = state.password,
                passwordVisible = state.passwordVisible,
                onUsuarioChange = loginViewModel::updateUsuario,
                onCorreoChange = loginViewModel::updateCorreo,
                onPasswordChange = loginViewModel::updatePassword,
                onPasswordVisibleChange = loginViewModel::changePasswordVisible
            )
            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            BotonesLogin(
                onLogInButtonClick = loginViewModel::onLoginButtonClick,
                onSignUpButtonClick = loginViewModel::onSignInButtonClick
            )
            Spacer(modifier = Modifier.height(25.dp))
            TextoLegal()
        }
    }
}

@Composable
fun FormLogin(
    correo: String,
    usuario: String,
    password: String,
    passwordVisible: Boolean,
    onCorreoChange: (String) -> Unit,
    onUsuarioChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibleChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        FieldLogin(
            label = stringResource(R.string.usuario),
            textDataField = usuario,
            onTextDataFieldChange = onUsuarioChange,
            modifier = Modifier.testTag("txtFieldUsuario")
        )

        FieldLogin(
            label = stringResource(R.string.correo),
            textDataField = correo,
            onTextDataFieldChange = onCorreoChange,
            modifier = Modifier.testTag("txtFieldCorreo")
        )

        PasswordField(
            label = stringResource(R.string.password),
            textDataField = password,
            passwordVisible = passwordVisible,
            onTextDataFieldChange = onPasswordChange,
            onPasswordVisibleChange = onPasswordVisibleChange,
            modifier = Modifier.testTag("txtFieldPassword")
        )
    }
}

@Composable
fun PasswordField(
    label: String,
    textDataField: String,
    passwordVisible: Boolean,
    onPasswordVisibleChange: () -> Unit,
    onTextDataFieldChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = textDataField,
        onValueChange = onTextDataFieldChange,
        label = { Text(text = label, color = MaterialTheme.colorScheme.onPrimary) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            cursorColor = MaterialTheme.colorScheme.onPrimary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.fillMaxWidth(),
        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            Button(
                onClick = onPasswordVisibleChange,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(painter = painterResource(
                    id = if(passwordVisible) R.drawable.eye_password_icon_show
                    else R.drawable.eye_password_icon_hide
                ),
                    contentDescription = stringResource(R.string.icono_ocultar_mostrar_password),
                    tint = Color.White,
                    modifier = Modifier.size(33.dp)
                )
            }
        }
    )
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
        label = { Text(text = label, color = MaterialTheme.colorScheme.onPrimary) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            cursorColor = MaterialTheme.colorScheme.onPrimary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.fillMaxWidth()
    )

}

@Composable
fun BotonesLogin(
    onLogInButtonClick: () -> Unit,
    onSignUpButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Button(
            onClick = onLogInButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.verde_claro)
            ),
            modifier = Modifier.fillMaxWidth().testTag("btnLogin")
        ) {
            Text(stringResource(R.string.log_in))
        }

        Button(
            onClick = onSignUpButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth().testTag("btnSignUp")
        ) {
            Text(stringResource(R.string.sign_up), color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
fun TextoLegal(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.by_continuing_you_agree_to_our_terms_of_service_and_privacy_policy),
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 13.sp,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    )
}


@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginScreen(
        loginViewModel = viewModel()
    )
}