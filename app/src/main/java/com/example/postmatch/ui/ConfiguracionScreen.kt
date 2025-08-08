package com.example.postmatch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.postmatch.R


@Composable
fun ConfiguracionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.verde_oscuro))
    ) {
        // Imagen de perfil y nombre
        ImagenPerfil()

        // Sección Cuenta
        SeccionConfiguracion(
            titulo = stringResource(R.string.cuenta),
            opciones = listOf(
                OpcionData("Modificar perfil", "Modifica tu nombre, foto de perfil y más", R.drawable.user_icon),
                OpcionData("Gestionar notificaciones", "Gestiona tus notificaciones", R.drawable.notification_icon),
                OpcionData("Cambiar contraseña", "Cambia tu contraseña", R.drawable.password_icon),
                OpcionData("Cuentas vinculadas", "Gestiona tus cuentas vinculadas", R.drawable.link_icon)
            )
        )

        // Sección Ayuda
        SeccionConfiguracion(
            titulo = "Ayuda",
            opciones = listOf(
                OpcionData("Preguntas frecuentes", "Preguntas frecuentes", R.drawable.faq_icon),
                OpcionData("Contacta con nosotros", "Contacta con nosotros", R.drawable.email_icon),
                OpcionData("Política de privacidad", "Política de privacidad", R.drawable.personal_data_protection_icon),
                OpcionData("Términos y condiciones", "Términos y condiciones", R.drawable.terms_and_conditions_icon)
            )
        )

        // Botón Cerrar sesión
        Button(
            onClick = { /* Acción logout */ },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.verde_claro)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Cerrar sesión", color = Color.White)
        }
    }
}

@Composable
fun SeccionConfiguracion(
    titulo: String,
    opciones: List<OpcionData>,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = titulo,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.verde))
                .padding(vertical = 8.dp)
        ) {
            opciones.forEach { opcion ->
                ItemOpcion(opcion)
            }
        }
    }
}

@Composable
fun ItemOpcion(
    opcion: OpcionData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { /* Acción */ }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono en recuadro
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = opcion.icono),
                contentDescription = opcion.titulo,
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Título y subtítulo
        Column(modifier = Modifier.weight(1f)) {
            Text(opcion.titulo, fontWeight = FontWeight.Bold, color = Color.White)
            Text(opcion.subtitulo, fontSize = 12.sp, color = Color.LightGray)
        }

        // Flecha
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Ir",
            tint = Color.White
        )
    }
}

@Composable
fun ImagenPerfil(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.verde_oscuro))
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen de perfil circular
        Image(
            painter = painterResource(id = R.drawable.ricardo_icon), // pon aquí tu imagen de perfil mock
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.White), // fondo blanco por si la imagen no llena el círculo
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Nombre
        Text(
            text = "Ricardo",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )

        // Usuario
        Text(
            text = "@ricardo",
            fontSize = 14.sp,
            color = Color.LightGray
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ConfiguracionScreenPreview() {
    ConfiguracionScreen()
}

data class OpcionData(val titulo: String, val subtitulo: String, val icono: Int)
