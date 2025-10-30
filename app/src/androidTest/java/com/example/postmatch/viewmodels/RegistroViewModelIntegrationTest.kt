package com.example.postmatch.viewmodels

import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.example.postmatch.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.postmatch.data.repository.AuthRepository
import com.example.postmatch.data.repository.UsuarioRepository
import com.example.postmatch.ui.Screens.registro.RegistroViewModel
import com.google.common.truth.Truth
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

// video tiempo: 2:01:01 - 2:26:35
class RegistroViewModelIntegrationTest {

    private lateinit var viewModel: RegistroViewModel

    @Before
    fun setUp() {
        try {
            Firebase.firestore.useEmulator("10.0.2.2", 8080)
            Firebase.auth.useEmulator("10.0.2.2", 9099)
        } catch(e: Exception) { }

        val authRepository = AuthRepository(AuthRemoteDataSource(Firebase.auth))
        val usuarioRepository =
            UsuarioRepository(UserFirestoreDataSourceImpl(Firebase.firestore),
                AuthRemoteDataSource(Firebase.auth))
        viewModel = RegistroViewModel(authRepository, usuarioRepository)
    }

    @After
    fun tearDown() = runTest {
        val user = Firebase.auth.currentUser
        if(user != null) {
            Firebase.auth.signOut()
        }
        user?.delete()?.await()
    }

    @Test
    fun registro_success_createUserAndUpdateUI() = runTest {

        // Arrange
        viewModel.updateNombre("nombre")
        viewModel.updateEmail("nuevo@gmail")
        viewModel.updatePassword("123456")
        viewModel.updateUrlFotoPerfil("foto.png")

        // Act
        viewModel.register()

        // Assert
        val state = viewModel.uiState.value
        Truth.assertThat(state.nombre).isEqualTo("nombre")
        Truth.assertThat(state.email).isEqualTo("nuevo@gmail")
        Truth.assertThat(state.password).isEqualTo("123456")
        Truth.assertThat(state.urlFotoPerfil).isEqualTo("foto.png")
    }
}