package com.example.postmatch.data.local

import android.util.Log
import com.example.postmatch.R
import com.example.postmatch.data.OpcionConfiguracionButtonInfo
import com.example.postmatch.data.SeccionConfiguracionInfo

object LocalSeccionConfiguracionProvider {


    val seccionesConfiguracion = listOf(
        SeccionConfiguracionInfo(
            titulo = "Cuenta",
            opcionesConfiguracion = listOf(
                OpcionConfiguracionButtonInfo(
                    titulo = "Modificar perfil",
                    subtitulo = "Modifica tu nombre, foto de perfil y más",
                    idIcono = R.drawable.user_icon,
                    onClick = { Log.d("ConfiguracionScreen","Modificar perfil")}
                ),
                OpcionConfiguracionButtonInfo(
                    titulo = "Gestionar notificaciones",
                    subtitulo =  "Gestiona tus notificaciones",
                    idIcono = R.drawable.notification_icon,
                    onClick = { Log.d("ConfiguracionScreen","Gestionar notificaciones")}
                ),
                OpcionConfiguracionButtonInfo(
                    titulo = "Cambiar contraseña",
                    subtitulo =  "Cambia tu contraseña",
                    idIcono = R.drawable.password_icon,
                    onClick = { Log.d("ConfiguracionScreen","Cambiar contraseña")}
                ),
                OpcionConfiguracionButtonInfo(
                    titulo = "Cuentas vinculadas",
                    subtitulo =  "Gestiona tus cuentas vinculadas",
                    idIcono = R.drawable.link_icon,
                    onClick = { Log.d("ConfiguracionScreen","Cuentas vinculadas")}
                )
            )
        ),
        SeccionConfiguracionInfo(
            titulo = "Ayuda",
            opcionesConfiguracion = listOf(
                OpcionConfiguracionButtonInfo(
                    titulo = "Preguntas frecuentes",
                    subtitulo =  "Preguntas frecuentes",
                    idIcono = R.drawable.faq_icon,
                    onClick = { Log.d("ConfiguracionScreen","Preguntas frecuentes")}
                ),
                OpcionConfiguracionButtonInfo(
                    titulo = "Contacta con nosotros",
                    subtitulo =  "Contacta con nosotros",
                    idIcono = R.drawable.email_icon,
                    onClick = { Log.d("ConfiguracionScreen","Contacta con nosotros")}
                ),
                OpcionConfiguracionButtonInfo(
                    titulo = "Política de privacidad",
                    subtitulo =  "Política de privacidad",
                    idIcono = R.drawable.personal_data_protection_icon,
                    onClick = { Log.d("ConfiguracionScreen","Política de privacidad")}
                ),
                OpcionConfiguracionButtonInfo(
                    titulo = "Términos y condiciones",
                    subtitulo =  "Términos y condiciones",
                    idIcono = R.drawable.terms_and_conditions_icon,
                    onClick = { Log.d("ConfiguracionScreen","Términos y condiciones")}
                )
            )
        )
    )
}