package com.example.postmatch

import com.example.postmatch.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.postmatch.data.dtos.RegisterUserDto
import com.example.postmatch.data.dtos.UsuarioDto
import com.google.common.truth.Truth
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class FirebaseUserDataSourceTest {

    private val db = Firebase.firestore
    private lateinit var dataSource: UserFirestoreDataSourceImpl


    @Before
    fun setUp() = runTest{
        try {
            db.useEmulator("10.0.2.2",8080)
        } catch (e: Exception) { }

        dataSource = UserFirestoreDataSourceImpl(db)
        val batch = db.batch()
        repeat(10) { i ->
            val usuario = generateUsuario(i)
            batch.set(db.collection("users").document(usuario.id), usuario)
        }
        batch.commit().await()
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
    fun getUsuarioById_validId_correctUsuarioReturned() = runTest {

        // Arrange
        val id = "id8"
        val expectedNombre = "nombre8"
        // Act
        val result = dataSource.getUsuarioById(id,"testUser")
        // Assert
        //Truth.assertThat(result.id).isNotNull()
        Truth.assertThat(result?.nombre).isEqualTo(expectedNombre)
        Truth.assertThat(result?.id).isEqualTo(id)
    }

    @Test
    fun getAllUsuarios_validData_returnsAllUsuarios() = runTest {
        // Arrange
        val expectedCount = 10

        // Act
        val usuarios = dataSource.getAllUsuarios()

        // Assert
        Truth.assertThat(usuarios).isNotEmpty()
        Truth.assertThat(usuarios.size).isEqualTo(expectedCount)
        Truth.assertThat(usuarios.map { it.nombre }).contains("nombre5")
    }

    @Test
    fun registerUser_validData_userIsRegistered() = runTest {
        // Arrange
        val newUserId = "customUserId"
        val newUser = RegisterUserDto(
            nombre = "Juan Pérez",
            email = "juan@example.com",
            password = "123456",
            fotoPerfilUrl = "fotoPerfilUrl123"
        )

        // Act
        dataSource.registerUser(newUser, newUserId)
        val storedUser = db.collection("users").document(newUserId).get().await()

        // Assert
        Truth.assertThat(storedUser.exists()).isTrue()
        Truth.assertThat(storedUser.getString("nombre")).isEqualTo(newUser.nombre)
        Truth.assertThat(storedUser.getString("email")).isEqualTo(newUser.email)
        Truth.assertThat(storedUser.getString("fotoPerfilUrl")).isEqualTo(newUser.fotoPerfilUrl)
    }
   /*
    @Test
    fun seguirTantoDejarDeSeguirUsuario_seguirUsuario_UsuarioSeguido() = runTest {
        // Arrange
        val idCurrentUser = "id1"
        val idUserToFollow = "id2"

        // Act
        val currentUser = dataSource.getUsuarioById(idCurrentUser,idCurrentUser)
        val targetUser = dataSource.getUsuarioById(idUserToFollow,idCurrentUser)

        // Act
        dataSource.seguirTantoDejarDeSeguirUsuario(currentUser.id, targetUser.id)
        val targetUserResult = dataSource.getUsuarioById(targetUser.id,currentUser.id)
        Truth.assertThat(targetUserResult.followed).isEqualTo(true)
    }

    */
}