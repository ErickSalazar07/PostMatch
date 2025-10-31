package com.example.postmatch.viewmodel

import com.example.postmatch.data.repository.AuthRepository
import com.example.postmatch.ui.Screens.login.LoginViewModel
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


class LoginViewModelUnitTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var authRepository: AuthRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        authRepository = mockk()
        every { authRepository.currentUser } returns null // Mock del currentUser
        viewModel = LoginViewModel(authRepository, StandardTestDispatcher()) // Pasar el TestDispatcher
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun onLoginButtonClick_withValidCredentials_callsAuthRepositoryAndInvokesCallback() = runTest {
        val testEmail = "test@test.com"
        val testPassword = "password123"
        var callbackInvoked = false

        viewModel.updateCorreo(testEmail)
        viewModel.updatePassword(testPassword)
        viewModel.setLoginButtonClick { callbackInvoked = true }

        coEvery { authRepository.signIn(testEmail, testPassword) } returns Result.success(mockk())

        viewModel.onLoginButtonClick()
        advanceUntilIdle()

        // Verificar que se llamó al repositorio con los parámetros correctos
        coVerify(exactly = 1) { authRepository.signIn(testEmail, testPassword) }

        // Verificar que se invocó el callback de éxito
        assertThat(callbackInvoked).isTrue()

        // Verificar que no hay mensaje de error
        assertThat(viewModel.uiState.value.errorMessage).isNull()
    }

    @Test
    fun onLoginButtonClick_withInvalidCredentials_setsErrorMessage() = runTest {
        val testEmail = "wrong@test.com"
        val testPassword = "wrongpassword"
        val errorMessage = "Credenciales inválidas"
        var callbackInvoked = false

        viewModel.updateCorreo(testEmail)
        viewModel.updatePassword(testPassword)
        viewModel.setLoginButtonClick { callbackInvoked = true }

        coEvery { authRepository.signIn(testEmail, testPassword) } returns
                Result.failure(Exception(errorMessage))

        viewModel.onLoginButtonClick()
        advanceUntilIdle()

        // Verificar que se llamó al repositorio
        coVerify(exactly = 1) { authRepository.signIn(testEmail, testPassword) }

        // Verificar que NO se invocó el callback (login falló)
        assertThat(callbackInvoked).isFalse()

        // Verificar que se estableció el mensaje de error
        val state = viewModel.uiState.value
        println("Error message: ${state.errorMessage}") // Debug
        assertThat(state.errorMessage).isNotNull()
        assertThat(state.errorMessage).isEqualTo(errorMessage)
    }

    @Test
    fun onLoginButtonClick_trimsWhitespaceFromCredentials() = runTest {
        val emailWithSpaces = "  test@test.com  "
        val passwordWithSpaces = "  password123  "
        val trimmedEmail = "test@test.com"
        val trimmedPassword = "password123"

        viewModel.updateCorreo(emailWithSpaces)
        viewModel.updatePassword(passwordWithSpaces)

        coEvery { authRepository.signIn(trimmedEmail, trimmedPassword) } returns Result.success(
            mockk()
        )

        viewModel.onLoginButtonClick()
        advanceUntilIdle()

        // Verificar que se llamó con valores sin espacios
        coVerify(exactly = 1) { authRepository.signIn(trimmedEmail, trimmedPassword) }
    }
}