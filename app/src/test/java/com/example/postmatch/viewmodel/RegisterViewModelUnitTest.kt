package com.example.postmatch.viewmodel

import com.example.postmatch.data.repository.AuthRepository
import com.example.postmatch.data.repository.UsuarioRepository
import com.example.postmatch.ui.Screens.registro.RegistroViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class RegisterViewModelUnitTest {

    private lateinit var viewmodel: RegistroViewModel

    private lateinit var authRepository: AuthRepository

    private lateinit var userRepository: UsuarioRepository


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
        Dispatchers.setMain(StandardTestDispatcher())
        authRepository = mockk()
        userRepository = mockk()
        viewmodel = RegistroViewModel(authRepository, userRepository,StandardTestDispatcher())


    }

    @Test
    fun register_sucess_createUserAndUpdateUI() = runTest {


        viewmodel.updateEmail("test@test.com")
        viewmodel.updatePassword("123456")
        viewmodel.updateNombre("test")
        viewmodel.updateUrlFotoPerfil("test")


        coEvery { authRepository.signUp(any(),any() ) } returns Result.success(Unit)


        coEvery { authRepository.currentUser?.uid }returns "1"

        coEvery { userRepository.registerUser(
            any(),
            any(),
            any(),
            any(),
            any()
        ) }returns Result.success(Unit)

        viewmodel.registerUserOnline()

        advanceUntilIdle()

        val state = viewmodel.uiState.value
        assertThat(state.errorMessage).isNull()


    }


    @Test
    fun register_withEmptyFields_showsErrorMessage() = runTest {
        // Dejar campos vacíos o solo con espacios
        viewmodel.updateEmail("  ")
        viewmodel.updatePassword("  ")
        viewmodel.updateNombre("  ")

        viewmodel.registerUserOnline()
        advanceUntilIdle()

        val state = viewmodel.uiState.value
        assertThat(state.errorMessage).isEqualTo("Todos los campos son obligatorios")

        // Verificar que no se llamó a los repositorios
        coVerify(exactly = 0) { authRepository.signUp(any(), any()) }
        coVerify(exactly = 0) { userRepository.registerUser(any(), any(), any(), any(), any()) }
    }

    @Test
    fun register_withShortPassword_showsErrorMessage() = runTest {
        viewmodel.updateEmail("test@test.com")
        viewmodel.updatePassword("12345") // Solo 5 caracteres
        viewmodel.updateNombre("test")
        viewmodel.updateUrlFotoPerfil("test")

        viewmodel.registerUserOnline()
        advanceUntilIdle()

        val state = viewmodel.uiState.value
        assertThat(state.errorMessage).isEqualTo("La contraseña debe tener al menos 6 caracteres")

        // Verificar que no se llamó a los repositorios
        coVerify(exactly = 0) { authRepository.signUp(any(), any()) }
        coVerify(exactly = 0) { userRepository.registerUser(any(), any(), any(), any(), any()) }
    }

    @Test
    fun register_authRepositoryFails_showsErrorMessage() = runTest {
        viewmodel.updateEmail("test@test.com")
        viewmodel.updatePassword("123456")
        viewmodel.updateNombre("test")
        viewmodel.updateUrlFotoPerfil("test")

        // Simular fallo en el signUp
        coEvery { authRepository.signUp(any(), any()) } returns Result.failure(Exception("Network error"))

        viewmodel.registerUserOnline()
        advanceUntilIdle()

        val state = viewmodel.uiState.value
        assertThat(state.errorMessage).isEqualTo("Error al registrar usuario")

        // Verificar que no se llamó al userRepository
        coVerify(exactly = 0) { userRepository.registerUser(any(), any(), any(), any(), any()) }
    }





}