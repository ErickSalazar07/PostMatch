package com.example.postmatch.viewmodel

import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.example.postmatch.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.postmatch.data.repository.AuthRepository
import com.example.postmatch.data.repository.UsuarioRepository
import com.example.postmatch.ui.Screens.registro.RegistroViewModel
import com.google.common.truth.Truth.assertThat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.StandardTestDispatcher

import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class RegisterViewModelIntegrationTest {

    private lateinit var viewmodel : RegistroViewModel


    @Before
    fun setUp(){
        try {
            Firebase.auth.useEmulator("10.0.2.2", 9099)
            Firebase.firestore.useEmulator("10.0.2.2",8080)

        } catch (e: Exception) {




        }

        val authRepository = AuthRepository(AuthRemoteDataSource(Firebase.auth))
        val userRepository = UsuarioRepository(UserFirestoreDataSourceImpl(Firebase.firestore), AuthRemoteDataSource(Firebase.auth))

        val scheduler = TestCoroutineScheduler()
        val dispatcher = StandardTestDispatcher(scheduler)

        Dispatchers.setMain(dispatcher)
      viewmodel = RegistroViewModel(authRepository ,userRepository, dispatcher)

    }

    @Test
    fun register_failure_showsErrorMessage() = runTest {
        // Arrange
        val invalidEmail = "bademail"
        val password = "123456"
        viewmodel.updateEmail(invalidEmail)
        viewmodel.updatePassword(password)
        viewmodel.updateNombre("Invalid User")
        viewmodel.updateUrlFotoPerfil("https://fakeurl.com/photo.jpg")

        // Act
        viewmodel.registerUserOnline()

        // Assert
        val state = viewmodel.uiState.value
        assertThat(state.errorMessage).isNotNull()
        assertThat(state.success).isFalse()
    }



    /*

    @Test
    fun register_sucess_createUserAndUpdateUI() = runTest {


        viewmodel.updateEmail("test@test.com")
        viewmodel.updatePassword("123456")
        viewmodel.updateNombre("test")
        viewmodel.updateUrlFotoPerfil("test")

        viewmodel.registerUserOnline()

        val state = viewmodel.uiState.value
        assertThat(state.errorMessage).isNull()
        }

     */
    @Test
    fun register_success_createsUserAndStoresInFirestore() = runTest {
        // Arrange
        val email = "test_integration_${System.currentTimeMillis()}@test.com" // Email único para evitar conflictos
        val password = "123456"
        val nombre = "Test User"
        val urlFoto = "https://fakeurl.com/photo.jpg"

        viewmodel.updateEmail(email)
        viewmodel.updatePassword(password)
        viewmodel.updateNombre(nombre)
        viewmodel.updateUrlFotoPerfil(urlFoto)

        // Act
        viewmodel.registerUserOnline()

        // CRÍTICO: Esperar a que todas las coroutines terminen
        testScheduler.advanceUntilIdle()

        // Assert
        val state = viewmodel.uiState.value
        assertThat(state.errorMessage).isNull()
        assertThat(state.success).isTrue()

        // Verificar que el usuario se creó en Firebase Auth
        val currentUser = Firebase.auth.currentUser
        assertThat(currentUser).isNotNull()
        assertThat(currentUser?.email).isEqualTo(email)

        // Verificar que se creó el documento del usuario en Firestore
        val doc = Firebase.firestore.collection("usuarios")
            .document(currentUser!!.uid)
            .get()
            .await()

        assertThat(doc.exists()).isTrue()
        assertThat(doc.getString("nombre")).isEqualTo(nombre)
    }



    @After
    fun tearDown() = runTest{

        val user = Firebase.auth.currentUser
        if(user != null){

            Firebase.auth.signOut()
        }

        user?.delete()?.await()
    }
}