package com.example.postmatch.repository

import android.util.Log
import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.example.postmatch.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.postmatch.data.dtos.UsuarioDto
import com.example.postmatch.data.repository.UsuarioRepository
import com.google.common.truth.Truth
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class UsuarioRepositoryIntegrationTest {

    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private lateinit var usuarioRepository: UsuarioRepository

    @Before
    fun setUp() = runTest {
        try {
            db.useEmulator("10.0.2.2", 8080)
        } catch (e: Exception) { }
        usuarioRepository = UsuarioRepository(UserFirestoreDataSourceImpl(db), AuthRemoteDataSource(auth))

        val batch = db.batch()
        repeat(10) { i ->
            val usuario = generateUsuario(i)
            batch.set(db.collection("users").document(usuario.id), usuario)
        }
        Log.d("setUp", "beforesetUp: $batch")
        batch.commit().await()
        Log.d("setUp", "aftersetUp: $batch")
    }

    @After
    fun tearDown() = runTest{
        // delete users
        val usuarios = db.collection("users").get().await()
        usuarios.documents.forEach { doc ->
            db.collection("users").document(doc.id).delete().await()
        }
    }

    private fun generateUsuario(i: Int): UsuarioDto = UsuarioDto(
        id = "id$i",
        nombre = "nombre$i",
        email = "email$i",
        password = "password$i",
        fotoPerfilUrl = "fotoPerfilUrl$i",
        followed = false,
        numFollowed = 0,
        numFollowers = 0
    )

    @Test
    fun getUsuarioById_validId_UsuarioCorrect() = runTest {
       // Arrange
        val id = "id8"
        val expectedNombre = "nombre8"

        // Act
        val result = usuarioRepository.getUsuarioById(id)

        // Assert
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.nombre).isEqualTo(expectedNombre)
    }

    @Test
    fun getUsuarioById_invalidId_returnFailure() = runTest {
        // Arrange
        val id = "id8888888"

        // Act
        val result = usuarioRepository.getUsuarioById(id)

        // Assert
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result?.exceptionOrNull()?.message).isEqualTo("Ocurrió un error inesperado al obtener la información del usuario")
    }

    @Test
    fun getUsuarios_returnAllUsuarios() = runTest {
        val result = usuarioRepository.getUsuarios()

        Truth.assertThat(result.isSuccess).isTrue()
        val lista = result.getOrNull()
        Truth.assertThat(lista).isNotNull()
        Truth.assertThat(lista!!.size).isEqualTo(10)
    }

    @Test
    fun updateFotoPerfilById_validId_updatesSuccessfully() = runTest {
        val id = "id3"
        val newFoto = "nuevaFotoUrl"

        val result = usuarioRepository.updateFotoPerfilById(id, newFoto)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun updateFotoPerfilById_invalidId_returnsFailureMessage() = runTest {
        val id = "idInexistente"
        val newFoto = "fotoFakeUrl"

        val result = usuarioRepository.updateFotoPerfilById(id, newFoto)

        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()?.message)
            .isEqualTo("Ocurrió un error inesperado al actualizar la foto de perfil")
    }

    @Test
    fun registerUser_validData_registersSuccessfully() = runTest {
        val result = usuarioRepository.registerUser(
            nombre = "nuevoUsuario",
            email = "nuevo@correo.com",
            password = "123456",
            userId = "idNuevo",
            fotoPerfilUrl = "fotoNuevo"
        )

        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun updateUser_validUser_updatesSuccessfully() = runTest {
        val result = usuarioRepository.updateUser(
            userId = "id1",
            nombre = "nuevoNombre",
            email = "nuevo@email.com",
            password = "nuevaPass"
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun updateUser_invalidId_returnsFailureMessage() = runTest {
        val result = usuarioRepository.updateUser(
            userId = "idInexistente",
            nombre = "fake",
            email = "fake@email.com",
            password = "fakepass"
        )
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()?.message)
            .isEqualTo("Ocurrió un error inesperado al actualizar el usuario")
    }
}