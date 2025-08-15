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
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.postmatch.data.OpcionConfiguracionButtonInfo
import com.example.postmatch.data.SeccionConfiguracionInfo
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.local.LocalSeccionConfiguracionProvider
import com.example.postmatch.data.local.LocalUsuarioProvider


@Composable
fun ConfiguracionScreen(
    modifier: Modifier = Modifier
) {
    val usuario = LocalUsuarioProvider.usuarios[0]
    val secciones = LocalSeccionConfiguracionProvider.seccionesConfiguracion
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.verde_oscuro))
    ) {
        // Imagen de perfil y nombre
        ImagenPerfil(
            usuario = usuario
        )

        // Sección Cuenta
        LazyColumn(

        ) {
            items(secciones.size) {
                index ->
                SeccionConfiguracion(secciones[index])
            }
        }

        // Botón Cerrar sesión
        Button(
            onClick = { /* Acción logout */ },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.verde_claro)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(stringResource(R.string.cerrar_sesi_n), color = Color.White)
        }
    }
}

@Composable
fun SeccionConfiguracion(
    seccion: SeccionConfiguracionInfo,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = seccion.titulo,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.verde))
                .padding(vertical = 8.dp)
        ) {

            for(opcion in seccion.opcionesConfiguracion) {
                ItemOpcion(opcion)
            }
        }
    }
}

@Composable
fun ItemOpcion(
    opcion: OpcionConfiguracionButtonInfo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { /* Acción */ }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = opcion.onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            // Icono en recuadro
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = opcion.idIcono),
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
                contentDescription = stringResource(R.string.ir),
                tint = Color.White
            )
        }
    }
}

@Composable
fun ImagenPerfil(
    usuario: UsuarioInfo,
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
            contentDescription = stringResource(R.string.foto_de_perfil),
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.White), // fondo blanco por si la imagen no llena el círculo
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        // nombre
        Text(
            text = usuario.nombre,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )

        // correo
        Text(
            text = usuario.email,
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

