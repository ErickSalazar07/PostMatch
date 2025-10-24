package com.example.postmatch

import com.example.postmatch.data.datasource.impl.firestore.ReviewFirestoreDataSourceImpl
import com.example.postmatch.data.dtos.CreateReviewDto
import com.example.postmatch.data.dtos.PartidoCreateDto
import com.example.postmatch.data.dtos.ReviewDto
import com.example.postmatch.data.dtos.UsuarioCreateDto
import com.google.common.truth.Truth
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.Date

class FirebaseReviewDataSourceTest {
    private val db = Firebase.firestore
    private lateinit var dataSource: ReviewFirestoreDataSourceImpl


    @Before
    fun setUp() = runTest {
        try {
            db.useEmulator("10.0.2.2", 8080)
        } catch (e: Exception) { }
        dataSource = ReviewFirestoreDataSourceImpl(db)
        val batch = db.batch()
        repeat(10) { i ->
            val review = generateReview(i)
            batch.set(db.collection("reviews").document(review.id), review)
        }
        batch.commit().await()
    }

    @After
    fun tearDown() = runTest {
        val reviews = db.collection("reviews").get().await()
        reviews.documents.forEach { doc ->
            db.collection("reviews").document(doc.id).delete().await()
        }
    }

    private fun generateReview(i: Int): ReviewDto = ReviewDto(
        id = "review$i",
        titulo = "Título de la review $i",
        descripcion = "Esta es la descripción de la review número $i. Muy interesante y útil.",
        fecha = Date(),
        calificacion = (1..5).random(),
        numLikes = i * 2,
        numComentarios = i,
        idUsuario = "usuario$i",
        idPartido = "partido$i",
        partido = PartidoCreateDto(
            fotoUrl = "https://fakeimage.com/partido$i.jpg",
            fecha = Date()
        ),
        usuario = UsuarioCreateDto(
            nombre = "Usuario $i",
            email = "usuario$i@example.com",
            fotoPerfilUrl = "https://fakeimage.com/perfil$i.jpg"
        ),
        likedByUser = (i % 2 == 0)
    )

    @Test
    fun getAllReviews_validData_returnsAllReviews() = runTest {
        // Act
        val reviews = dataSource.getAllReviews()

        // Assert
        Truth.assertThat(reviews).isNotEmpty()
        Truth.assertThat(reviews).hasSize(10)
        Truth.assertThat(reviews.first().id).isEqualTo("review0")
    }

    @Test
    fun getReviewById_existingId_returnsCorrectReview() = runTest {
        // Act
        val review = dataSource.getReviewById("review5")

        // Assert
        Truth.assertThat(review).isNotNull()
        Truth.assertThat(review.id).isEqualTo("review5")
        Truth.assertThat(review.titulo).isEqualTo("Título de la review 5")
        Truth.assertThat(review.idUsuario).isEqualTo("usuario5")
    }

    @Test
    fun createReview_validData_reviewIsCreated() = runTest {
        // Arrange
        val newReview = CreateReviewDto(
            titulo = "Nueva review de prueba",
            descripcion = "Creada durante los tests unitarios.",
            fecha = Date(),
            calificacion = 4,
            idUsuario = "usuario10",
            idPartido = "partido10"
        )

        // Act
        dataSource.createReview(newReview)
        val reviews = dataSource.getAllReviews()
        val found = reviews.find { it.idUsuario == newReview.idUsuario }

        // Assert
        Truth.assertThat(found).isNotNull()
        Truth.assertThat(found?.titulo).isEqualTo(newReview.titulo)
        Truth.assertThat(found?.idUsuario).isEqualTo(newReview.idUsuario)
        Truth.assertThat(found?.idPartido).isEqualTo(newReview.idPartido)
        Truth.assertThat(found?.calificacion).isEqualTo(newReview.calificacion)
    }
}