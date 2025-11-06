package com.example.postmatch.e2e

import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.postmatch.MainActivity
import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.example.postmatch.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.postmatch.data.repository.AuthRepository
import com.example.postmatch.data.repository.UsuarioRepository
import com.example.postmatch.ui.navigation.Screen
import com.google.common.truth.Truth.assertThat
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CasoDeUso02 {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var authRepository: AuthRepository

    private lateinit var usuarioRepository: UsuarioRepository

    @Before
    fun setUp() {
        hiltRule.inject()
        try {
            Firebase.firestore.useEmulator("10.0.2.2", 8080)
            Firebase.auth.useEmulator("10.0.2.2", 9099)
        } catch(_: Exception) { }

        val authRemoteDataSource = AuthRemoteDataSource(Firebase.auth)
        val usuarioRemoteDataSource = UserFirestoreDataSourceImpl(Firebase.firestore)
        authRepository = AuthRepository(authRemoteDataSource)
        usuarioRepository = UsuarioRepository(usuarioRemoteDataSource, authRemoteDataSource)

        runBlocking {
            authRepository.signUp("prueba@prueba.com","123456")
            authRepository.signIn("prueba@prueba.com","123456")

            val userId = authRepository.currentUser?.uid ?: return@runBlocking

            usuarioRepository.registerUser(
                nombre = "prueba",
                email = "prueba@prueba.com",
                userId = userId,
                fotoPerfilUrl = "",
                password = "123456"
            )
        }
    }

    @After
    fun tearDown() = runTest {
        val usuario = Firebase.auth.currentUser
        if (usuario != null) {
            Firebase.auth.signOut()
        }
        val idCurrentUser = usuario?.uid ?: return@runTest
        usuario.delete().await()
        Firebase.firestore.collection("users").document(idCurrentUser).delete().await()
    }

    @Test
    fun casoDeUso02() {
        composeRule.onNodeWithTag("splashScreen").assertIsDisplayed()
        // inicialmente estamos en ReviewsScreen
        composeRule.waitUntil(
            condition = { composeRule.onAllNodesWithTag("reviewsScreen").fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 5000 // 5 segundos de espera máxima
        )
        composeRule.onNodeWithTag("reviewsScreen").assertIsDisplayed()

        composeRule.waitUntil(
            condition = { composeRule.onAllNodesWithContentDescription(Screen.Notificaciones.route).fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 5000 // 5 segundos de espera máxima
        )

        // vamos a la seccion notificacion
        composeRule.onNodeWithContentDescription(Screen.Notificaciones.route).performClick()
        composeRule.waitUntil(
            condition = { composeRule.onAllNodesWithTag("notificacionesScreen").fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 5000 // 5 segundos de espera máxima
        )
        composeRule.onNodeWithTag("notificacionesScreen").assertIsDisplayed()
        composeRule.onAllNodesWithTag("itemUsuarioNotificacionButton").onFirst().performClick()
        Thread.sleep(3000)

        // vamos al perfil de ese otro usuario
        composeRule.onNodeWithTag("txtPerfilCorreo").assertTextEquals("justice@example.com")
        composeRule.waitUntil(5000){
            composeRule.onAllNodesWithTag("numSeguidores").fetchSemanticsNodes().isNotEmpty()
        }
        Thread.sleep(3000)
        composeRule.onAllNodesWithTag("numSeguidores").onFirst().assertTextEquals("0")
        composeRule.waitUntil(5000){
            composeRule.onAllNodesWithTag("seguirButton").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithTag("seguirButton").performClick()

        composeRule.waitUntil(5000){
            composeRule.onAllNodesWithTag("numSeguidores").fetchSemanticsNodes().isNotEmpty()
        }
        Thread.sleep(3000)
        composeRule.onNodeWithTag("numSeguidores").assertTextEquals("1")
        composeRule.waitUntil(
            condition = { composeRule.onAllNodesWithContentDescription(Screen.ReviewsFollow.route).fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 5000 // 5 segundos de espera máxima
        )
        composeRule.onNodeWithContentDescription(Screen.ReviewsFollow.route).performClick()
        Thread.sleep(3000)
        composeRule.waitUntil(
            condition = { composeRule.onAllNodesWithTag("reviewFollowItem").fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 5000 // 5 segundos de espera máxima
        )
        val reviewCards = composeRule.onAllNodesWithTag("reviewFollowItem").fetchSemanticsNodes()
        assertThat(reviewCards.size).isAtLeast(1)
    }
}