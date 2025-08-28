package com.example.postmatch.data.local

import com.example.postmatch.R
import com.example.postmatch.data.ReseniaMiPerfilInfo
import com.example.postmatch.ui.perfil.ReseniaPerfilData

object LocalReseniaMiPerfilData {
    val reseniasMiPerfil = listOf(
        ReseniaMiPerfilInfo(
            1,
            5,
            "Excelente servicio",
            "Me encantó la atención y el lugar",
            R.drawable.estadio_bernabeu
        ),
        ReseniaMiPerfilInfo(2, 4, "Muy bueno", "Todo estuvo bien, pero se puede mejorar", R.drawable.estadio_bernabeu),
        ReseniaMiPerfilInfo(3, 3, "Regular", "La experiencia fue aceptable", R.drawable.estadio_bernabeu),
        ReseniaMiPerfilInfo(4, 5, "Perfecto", "Superó mis expectativas", R.drawable.estadio_bernabeu),
        ReseniaMiPerfilInfo(5, 2, "Mala experiencia", "No quedé satisfecho con el servicio", R.drawable.estadio_bernabeu),
        ReseniaMiPerfilInfo(6, 4, "Muy agradable", "Volvería sin dudarlo", R.drawable.estadio_bernabeu)
    )
}