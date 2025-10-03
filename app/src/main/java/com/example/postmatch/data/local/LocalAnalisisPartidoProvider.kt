package com.example.postmatch.data.local

import com.example.postmatch.R
import com.example.postmatch.data.ReseniaAnalisisPartidoInfo

object LocalAnalisisPartidoProvider {
    var reseniasAnalisisPartido = listOf(
        ReseniaAnalisisPartidoInfo(
            idResenia = "1",
            fotoPerfil =  "https://firebasestorage.googleapis.com/v0/b/postmatch-c2ddd.firebasestorage.app/o/profileImages%2F4thdimen_pp.jpg?alt=media&token=f8efb70e-1969-46ad-ad0c-a8bcc42ab0ec",
            nSemanas = 1,
            nombreReseniador = "Juan Pérez",
            nEstrellas = 5,
            nLikes = 120,
            resenia = "Excelente análisis, muy detallado y fácil de entender."),
        ReseniaAnalisisPartidoInfo("2", "https://firebasestorage.googleapis.com/v0/b/postmatch-c2ddd.firebasestorage.app/o/profileImages%2F4thdimen_pp.jpg?alt=media&token=f8efb70e-1969-46ad-ad0c-a8bcc42ab0ec", 2, "María López", 4, 85, "Muy bueno, aunque podría ser más conciso."),
        ReseniaAnalisisPartidoInfo("3", "https://firebasestorage.googleapis.com/v0/b/postmatch-c2ddd.firebasestorage.app/o/profileImages%2F50cent_pp.jpg?alt=media&token=97e2d9c5-b1ec-4672-8de6-07e8b342ed1b", 3, "Carlos Gómez", 3, 40, "Interesante, pero faltaron algunos detalles del partido."),
        ReseniaAnalisisPartidoInfo("4", "https://firebasestorage.googleapis.com/v0/b/postmatch-c2ddd.firebasestorage.app/o/profileImages%2FSin%20t%C3%ADtulo.jpg?alt=media&token=58c8299d-c312-4d6b-98c4-e2927809d519", 4, "Lucía Fernández", 5, 200, "Me encantó la forma en que se explicó todo."),
        ReseniaAnalisisPartidoInfo("5", "https://firebasestorage.googleapis.com/v0/b/postmatch-c2ddd.firebasestorage.app/o/profileImages%2Fdefault_xbox360.jpg?alt=media&token=8a814635-c88a-44b6-829d-937381f751e1", 5, "Pedro Martínez", 2, 15, "Poca información y poco análisis."),
        ReseniaAnalisisPartidoInfo("6", "https://firebasestorage.googleapis.com/v0/b/postmatch-c2ddd.firebasestorage.app/o/profileImages%2Fsan_pp.jpg?alt=media&token=ac786d04-b728-4d57-9b99-a975cb4a2ae8", 6, "Ana Rodríguez", 4, 95, "Buen trabajo, volveré a leer sus reseñas.")
    )
}