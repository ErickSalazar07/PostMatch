package com.example.postmatch

import com.example.postmatch.data.datasource.impl.firestore.PartidoFirestoreDataSourceImpl
import com.example.postmatch.data.dtos.PartidoDto
import com.google.common.truth.Truth
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.Date

class FirebasePartidoDataSourceTest {
    private val db = Firebase.firestore
    private lateinit var dataSource: PartidoFirestoreDataSourceImpl

    @Before
    fun setUp() = runTest {
        try {
            db.useEmulator("10.0.2.2", 8080)
        } catch (e: Exception) { }
        dataSource = PartidoFirestoreDataSourceImpl(db)
        val batch = db.batch()
        repeat(10) { i ->
            val partido = generatePartido(i)
            batch.set(db.collection("partidos").document(partido.id), partido)
        }
        batch.commit().await()
    }

    @After
    fun tearDown() = runTest {
        val partidos = db.collection("partidos").get().await()
        partidos.documents.forEach { doc ->
            db.collection("partidos").document(doc.id).delete().await()
        }
    }

    private fun generatePartido(i: Int): PartidoDto = PartidoDto(
        id = "partido$i",
        nombre = "Partido de prueba $i",
        categoria = when (i % 3) {
            0 -> "Liga Profesional"
            1 -> "Copa Nacional"
            else -> "Amistoso"
        },
        visitante = "Equipo Visitante $i",
        local = "Equipo Local $i",
        golesLocal = (0..5).random(),
        golesVisitante = (0..5).random(),
        posesionLocal = (40..60).random(),
        posesionVisitante = 100 - (40..60).random(), // para que sumen aprox 100
        tirosLocal = (5..15).random(),
        tirosVisitante = (5..15).random(),
        fecha = Date(System.currentTimeMillis() - i * 86_400_000L), // días hacia atrás
        partidoFotoUrl = "https://fakeimage.com/partido$i.jpg"
    )

    @Test
    fun getAllPartidos_retornaListaCompleta() = runTest {
        // Act
        val result = dataSource.getAllPartidos()

        // Assert
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result).isNotEmpty()
        Truth.assertThat(result.size).isEqualTo(10)
        Truth.assertThat(result.first().nombre).startsWith("Partido de prueba")
    }

    @Test
    fun getPartidoById_retornaPartidoCorrecto() = runTest {
        // Arrange
        val partidoId = "partido3"

        // Act
        val partido = dataSource.getPartidoById(partidoId)

        // Assert
        Truth.assertThat(partido).isNotNull()
        Truth.assertThat(partido.id).isEqualTo(partidoId)
        Truth.assertThat(partido.nombre).contains("3")
        Truth.assertThat(partido.local).isNotEmpty()
        Truth.assertThat(partido.partidoFotoUrl).contains("fakeimage.com")
    }
}