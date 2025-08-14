package com.example.postmatch.data.local

import com.example.postmatch.data.ReviewInfo
import kotlin.Int
import kotlin.String

object LocalReviewProvider {
    var reviews = listOf(
        ReviewInfo(
            idReview = 1,
            titulo = "Increible partido FCB vs RM",
            descripcion = "Increible partido muy bueno para ser verdad. Espero siga ganando el FCB, me gusta mucho ese club. 🎉",
            fecha = "07/08/2025",
            usuarioNombre = "Luis",
            usuarioEmail = "l@noemail",
            usuarioPassword = "123",
            usuarioFotoPerfil = "usuario1.png",
            partidoNombre = "FCB vs RM",
            partidoCategoria = "Champions",
            partidoVisitante = "FCB",
            partidoLocal = "RM",
            partidoGolesVisitante = 2,
            partidoGolesLocal = 1,
            partidoFecha = "05/06/2025"
        ),
        ReviewInfo(
            idReview = 2,
            titulo = "Victoria sufrida del PSG",
            descripcion = "El PSG ganó en el último minuto, Mbappé impresionante. Espero siga ganando el PSG, me gusta mucho ese club. ⚽🔥",
            fecha = "10/08/2025",
            usuarioNombre = "María",
            usuarioEmail = "m@noemail",
            usuarioPassword = "abc",
            usuarioFotoPerfil = "usuario2.png",
            partidoNombre = "PSG vs Chelsea",
            partidoCategoria = "Champions",
            partidoVisitante = "PSG",
            partidoLocal = "Chelsea",
            partidoGolesVisitante = 3,
            partidoGolesLocal = 2,
            partidoFecha = "09/08/2025"
        ),
        ReviewInfo(
            idReview = 3,
            titulo = "Remontada épica del Liverpool",
            descripcion = "Nadie lo esperaba, pero remontaron con dos goles en los últimos 5 minutos. Espero siga ganando el Liverpool, me gusta mucho ese club. 🔴",
            fecha = "11/08/2025",
            usuarioNombre = "Carlos",
            usuarioEmail = "c@noemail",
            usuarioPassword = "456",
            usuarioFotoPerfil = "usuario3.png",
            partidoNombre = "Liverpool vs Milan",
            partidoCategoria = "Champions",
            partidoVisitante = "Liverpool",
            partidoLocal = "Milan",
            partidoGolesVisitante = 4,
            partidoGolesLocal = 3,
            partidoFecha = "10/08/2025"
        ),
        ReviewInfo(
            idReview = 4,
            titulo = "Derrota inesperada del Bayern",
            descripcion = "El Bayern dominó, pero perdió en penales. Espero siga ganando el Ajax, me gusta mucho ese club. 😱",
            fecha = "12/08/2025",
            usuarioNombre = "Ana",
            usuarioEmail = "a@noemail",
            usuarioPassword = "789",
            usuarioFotoPerfil = "usuario4.png",
            partidoNombre = "Bayern vs Ajax",
            partidoCategoria = "Champions",
            partidoVisitante = "Bayern",
            partidoLocal = "Ajax",
            partidoGolesVisitante = 1,
            partidoGolesLocal = 1,
            partidoFecha = "11/08/2025"
        ),
        ReviewInfo(
            idReview = 5,
            titulo = "Empate intenso entre Inter y Juve",
            descripcion = "Partido muy parejo, ambos equipos lo dieron todo. Espero si jugando asi la Juve, me gusta mucho ese club.👏",
            fecha = "13/08/2025",
            usuarioNombre = "Pedro",
            usuarioEmail = "p@noemail",
            usuarioPassword = "000",
            usuarioFotoPerfil = "usuario5.png",
            partidoNombre = "Inter vs Juventus",
            partidoCategoria = "Serie A",
            partidoVisitante = "Inter",
            partidoLocal = "Juventus",
            partidoGolesVisitante = 2,
            partidoGolesLocal = 2,
            partidoFecha = "12/08/2025"
        ),
        ReviewInfo(
            idReview = 6,
            titulo = "Atlético sólido en defensa",
            descripcion = "Partido cerrado, pero Atlético supo aguantar y ganar con un golazo. Me alegro que gano el Atlético. Viva España. 🛡️",
            fecha = "14/08/2025",
            usuarioNombre = "Sofía",
            usuarioEmail = "s@noemail",
            usuarioPassword = "111",
            usuarioFotoPerfil = "usuario6.png",
            partidoNombre = "Atlético vs Sevilla",
            partidoCategoria = "LaLiga",
            partidoVisitante = "Atlético",
            partidoLocal = "Sevilla",
            partidoGolesVisitante = 1,
            partidoGolesLocal = 0,
            partidoFecha = "13/08/2025"
        )
    )
}