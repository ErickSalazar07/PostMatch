package com.example.postmatch.repository

import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.example.postmatch.data.datasource.impl.firestore.PartidoFirestoreDataSourceImpl
import com.example.postmatch.data.datasource.impl.firestore.ReviewFirestoreDataSourceImpl
import com.example.postmatch.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.postmatch.data.dtos.PartidoCreateDto
import com.example.postmatch.data.dtos.ReviewDto
import com.example.postmatch.data.dtos.UsuarioCreateDto
import com.example.postmatch.data.dtos.toReviewInfo
import com.example.postmatch.data.repository.ReviewRepository
import com.google.common.truth.Truth
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.Date

class ReviewRepositoryIntegrationTest {
    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private lateinit var reviewRepository: ReviewRepository


    @Before
    fun setUp() = runTest {
        try {
            db.useEmulator("10.0.2.2", 8080)
        } catch (e: Exception) { }
        reviewRepository = ReviewRepository(
            reviewRemoteDataSource = ReviewFirestoreDataSourceImpl(db),
            authRemoteDataSource = AuthRemoteDataSource(auth),
            usuarioRemoteDataSource = UserFirestoreDataSourceImpl(db),
            partidoRemoteDataSource = PartidoFirestoreDataSourceImpl(db)
        )

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
    fun deleteReviewById_invalidId_returnsFailureMessage() = runTest {
        val result = reviewRepository.deleteReviewById("reviewInexistente")

        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()?.message)
            .isEqualTo("Error interno al eliminar la reseña.")
    }

    @Test
    fun sendOrDeleteLike_validIds_successfullyUpdates() = runTest {
        val result = reviewRepository.sendOrDeleteLike("review2", "usuario1")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun sendOrDeleteLike_invalidReviewId_returnsFailureMessage() = runTest {
        val result = reviewRepository.sendOrDeleteLike("reviewInexistente", "usuarioFake")

        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()?.message)
            .isEqualTo("No se pudo registrar el 'me gusta' en Firestore.")
    }
}