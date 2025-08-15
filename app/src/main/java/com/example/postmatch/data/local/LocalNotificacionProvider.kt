package com.example.postmatch.data.local

import com.example.postmatch.data.NotificacionInfo
import com.example.postmatch.R

object LocalNotificacionProvider {
    var notificaciones = listOf(
        NotificacionInfo(
            idNotificacion = 1,
            nombreUsuario = "Juan Pérez",
            descripcion = "Le dio like a tu publicación",
            nSemanas = 2,
            idFotoPerfil = R.drawable.user_icon
        ),
        NotificacionInfo(2,"Juan Pérez", "Le dio like a tu publicación", 2, R.drawable.user_icon),
        NotificacionInfo(3,"María Gómez", "Le dio like a tu publicación", 1, R.drawable.user_icon),
        NotificacionInfo(4,"Pedro López", "Le dio like a tu publicación", 3, R.drawable.user_icon),
        NotificacionInfo(5,"Lucía Martínez", "Le dio like a tu publicación", 1, R.drawable.user_icon),
        NotificacionInfo(6,"Carlos Ramírez", "Le dio like a tu publicación", 4, R.drawable.user_icon),
        NotificacionInfo(7,"Andrea Torres", "Le dio like a tu publicación", 2, R.drawable.user_icon),
        NotificacionInfo(8,"Luis Hernández", "Le dio like a tu publicación", 5, R.drawable.user_icon),
        NotificacionInfo(9,"Camila Rojas", "Le dio like a tu publicación", 3, R.drawable.user_icon),
        NotificacionInfo(10,"Felipe Morales", "Le dio like a tu publicación", 1, R.drawable.user_icon),
        NotificacionInfo(11,"Paola García", "Le dio like a tu publicación", 2, R.drawable.user_icon),
        NotificacionInfo(12,"Mateo Fernández", "Le dio like a tu publicación", 1, R.drawable.user_icon),
        NotificacionInfo(13,"Sofía Castro", "Le dio like a tu publicación", 4, R.drawable.user_icon),
        NotificacionInfo(14,"David Ortiz", "Le dio like a tu publicación", 2, R.drawable.user_icon),
        NotificacionInfo(15,"Isabella Ruiz", "Le dio like a tu publicación", 3, R.drawable.user_icon),
        NotificacionInfo(16,"Jorge Navarro", "Le dio like a tu publicación", 1, R.drawable.user_icon)
    )
}