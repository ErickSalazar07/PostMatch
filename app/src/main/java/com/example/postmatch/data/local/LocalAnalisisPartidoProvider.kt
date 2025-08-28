package com.example.postmatch.data.local

import com.example.postmatch.R
import com.example.postmatch.data.ReseniaAnalisisPartidoInfo

object LocalAnalisisPartidoProvider {
    var reseniasAnalisisPartido = listOf(
        ReseniaAnalisisPartidoInfo(
            idResenia = 1,
            fotoPerfil =  R.drawable.ricardo_icon,
            nSemanas = 1,
            nombreReseniador = "Juan Pérez",
            nEstrellas = 5,
            nLikes = 120,
            resenia = "Excelente análisis, muy detallado y fácil de entender."),
        ReseniaAnalisisPartidoInfo(2,R.drawable.ricardo_icon, 2, "María López", 4, 85, "Muy bueno, aunque podría ser más conciso."),
        ReseniaAnalisisPartidoInfo(3,R.drawable.ricardo_icon, 3, "Carlos Gómez", 3, 40, "Interesante, pero faltaron algunos detalles del partido."),
        ReseniaAnalisisPartidoInfo(4,R.drawable.ricardo_icon, 4, "Lucía Fernández", 5, 200, "Me encantó la forma en que se explicó todo."),
        ReseniaAnalisisPartidoInfo(5,R.drawable.ricardo_icon, 5, "Pedro Martínez", 2, 15, "Poca información y poco análisis."),
        ReseniaAnalisisPartidoInfo(6,R.drawable.ricardo_icon, 6, "Ana Rodríguez", 4, 95, "Buen trabajo, volveré a leer sus reseñas.")
    )
}