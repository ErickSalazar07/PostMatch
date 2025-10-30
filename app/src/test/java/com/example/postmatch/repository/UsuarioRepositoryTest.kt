package com.example.postmatch.repository

import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.example.postmatch.data.datasource.UsuarioRemoteDataSource
import com.example.postmatch.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.postmatch.data.dtos.ReviewDto
import com.example.postmatch.data.dtos.UsuarioDto
import com.example.postmatch.data.repository.UsuarioRepository
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.util.Date


class UsuarioRepositoryTest {


    private val mockDataSource = mockk<UserFirestoreDataSourceImpl>()
    private val authDataSource = mockk<AuthRemoteDataSource>()

    private val repository = UsuarioRepository(mockDataSource, authDataSource)

    @Test

    fun `al llamar getUserById, si el id es valido retorna un Result con el userInfo`() = runTest {
        //AAA

        val dto = UsuarioDto(


            id = "123",
            nombre = "Juan",
            email = "william.henry.harrison@example-pet-store.com",
            password = "123456",
            fotoPerfilUrl = "https://www.google.com",
            followed = false,
            numFollowed = 0,
            numFollowers = 0

        )

        //Arrange
        coEvery { authDataSource.currentUser?.uid } returns "1"
        coEvery { mockDataSource.getUsuarioById("123", "1") } returns dto


        //ACT
        val result = repository.getUsuarioById("123")
        //ASSERT
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.idUsuario).isEqualTo("123")
        Truth.assertThat(result.getOrNull()?.nombre).isEqualTo("Juan")



    }

    @Test
    fun `al llamar getUserById, si el id es invalido retorna un Result con un error`() = runTest {

        //AAA
        coEvery {  authDataSource.currentUser?.uid } returns "1"
        coEvery { mockDataSource.getUsuarioById("999", "1") } returns null

        //Act
        val result = repository.getUsuarioById("999")

        //Assert

        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()?.message).isEqualTo("Usuario no encontrado")




    }

    @Test
    fun  `al llamar getUserById, si el datasource devuelve cambios vacios se mapean correctamente`() = runTest {

        //AAA
        val dto = UsuarioDto(
            id = "123",
            nombre = "",
            email = "",
            password = "",
            fotoPerfilUrl = null,
            followed = false,
            numFollowed = 0,
            numFollowers = 0


        )

        coEvery {  authDataSource.currentUser?.uid } returns "1"
        coEvery { mockDataSource.getUsuarioById("123", "1") } returns dto

        //ACT

        val result = repository.getUsuarioById("123")


        //Assert
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.idUsuario).isEqualTo("123")
        Truth.assertThat(result.getOrNull()?.fotoPerfil).isEqualTo("No hay Imagen")

    }
    @Test
    fun `al llamar getReviewsByUsuarioId, si el id es valido retorna un Result con la lista de reviews`() = runTest {
        // Arrange
        val reviewDto1 = ReviewDto(
            id = "1",
            titulo = "Buena experiencia",
            descripcion = "Excelente cancha",
            fecha = Date(),
            calificacion = 5,
            numLikes = 10,
            numComentarios = 3,
            idUsuario = "123",
            idPartido = "partido1",
            likedByUser = false
        )
        val reviewDto2 = ReviewDto(
            id = "2",
            titulo = "Muy recomendable",
            descripcion = "Perfecta",
            fecha = Date(),
            calificacion = 4,
            numLikes = 5,
            numComentarios = 2,
            idUsuario = "123",
            idPartido = "partido2",
            likedByUser = true
        )

        coEvery { mockDataSource.getReviewsByUsuarioId("123") } returns listOf(reviewDto1, reviewDto2)

        // Act
        val result = repository.getReviewsByUsuarioId("123")

        // Assert
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.size).isEqualTo(2)
        Truth.assertThat(result.getOrNull()?.get(0)?.idReview).isEqualTo("1")
    }

    @Test
    fun `al llamar getUsuarios, si no hay usuarios retorna lista vacia`() = runTest {
        // Arrange
        coEvery { mockDataSource.getAllUsuarios() } returns emptyList()

        // Act
        val result = repository.getUsuarios()

        // Assert
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isEmpty()
    }

    @Test
    fun `al llamar getUserById con usuario seguido, el campo followed es true`() = runTest {
        // Arrange
        val dto = UsuarioDto(
            id = "123",
            nombre = "Pedro",
            email = "pedro@example.com",
            password = "password",
            fotoPerfilUrl = "https://example.com/photo.jpg",
            followed = true,
            numFollowed = 10,
            numFollowers = 25
        )

        coEvery { authDataSource.currentUser?.uid } returns "1"
        coEvery { mockDataSource.getUsuarioById("123", "1") } returns dto

        // Act
        val result = repository.getUsuarioById("123")

        // Assert
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.followed).isTrue()
        Truth.assertThat(result.getOrNull()?.numFollowers).isEqualTo(25)
    }

    @Test
    fun `al llamar getUserById, los contadores de seguidores se mapean correctamente`() = runTest {
        // Arrange
        val dto = UsuarioDto(
            id = "456",
            nombre = "Maria",
            email = "maria@example.com",
            password = "pass123",
            fotoPerfilUrl = "https://example.com/maria.jpg",
            followed = false,
            numFollowed = 150,
            numFollowers = 200
        )

        coEvery { authDataSource.currentUser?.uid } returns "1"
        coEvery { mockDataSource.getUsuarioById("456", "1") } returns dto

        // Act
        val result = repository.getUsuarioById("456")

        // Assert
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.numFollowed).isEqualTo(150)
        Truth.assertThat(result.getOrNull()?.numFollowers).isEqualTo(200)
    }

    @Test
    fun `al llamar getUserById con el mismo id del usuario autenticado, retorna sus propios datos`() = runTest {
        // Arrange
        val dto = UsuarioDto(
            id = "1",
            nombre = "",
            email = "current@example.com",
            password = "",
            fotoPerfilUrl = "",
            followed = false,
            numFollowed = 50,
            numFollowers = 75
        )

        coEvery { authDataSource.currentUser?.uid } returns "1"
        coEvery { mockDataSource.getUsuarioById("1", "1") } returns dto

        // Act
        val result = repository.getUsuarioById("1")

        // Assert
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.idUsuario).isEqualTo("1")
        Truth.assertThat(result.getOrNull()?.email).isEqualTo("current@example.com")
    }

}