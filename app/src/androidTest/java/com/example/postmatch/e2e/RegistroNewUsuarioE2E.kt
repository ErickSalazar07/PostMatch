package com.example.postmatch.e2e

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import com.example.postmatch.MainActivity
import com.example.postmatch.data.datasource.AuthRemoteDataSource
import com.example.postmatch.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.postmatch.data.repository.AuthRepository
import com.example.postmatch.data.repository.UsuarioRepository
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
class RegistroNewUsuarioE2E {

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
            authRepository.signUp("admin@admin.com","123456")
            authRepository.signOut()
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
    fun navigate_fromStart_toLogin() {
        composeRule.onNodeWithTag("splashScreen").assertIsDisplayed()
        composeRule.waitUntil(
            condition = { composeRule.onAllNodesWithTag("loginScreen").fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 5000 // 5 segundos de espera máxima
        )
        composeRule.onNodeWithTag("loginScreen").assertIsDisplayed()
        composeRule.onNodeWithTag("btnSignUp").performClick()
        composeRule.onNodeWithTag("registroScreen").assertIsDisplayed()
        composeRule.onNodeWithTag("txtFieldNombre").performTextInput("test")
        composeRule.onNodeWithTag("txtFieldEmail").performTextInput("test@example.com")
        composeRule.onNodeWithTag("txtFieldPassword").performTextInput("1234")
        composeRule.onNodeWithTag("txtFieldUrlFotoPerfil").performTextInput("https://example.com/image.jpg")
        composeRule.onNodeWithTag("btnRegistrar").performClick()
        composeRule.onNodeWithTag("txtMsgError").assertTextEquals("La contraseña debe tener al menos 6 caracteres")

        // Volver a intentar registrarse
        composeRule.onNodeWithTag("txtFieldPassword").performTextClearance()
        composeRule.onNodeWithTag("txtFieldPassword").performTextInput("123456")
        composeRule.onNodeWithTag("btnRegistrar").performClick()
        // Ya se encuentra en la pantalla ReviewsScreen
        composeRule.waitUntil(
            condition = { composeRule.onAllNodesWithTag("reviewsScreen").fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 10000 // espera hasta 5 segundos
        )
        composeRule.onNodeWithTag("reviewsScreen").assertIsDisplayed()

        // Ya se encuentra en la pantalla ReviewsScreen
        composeRule.waitUntil(
            condition = { composeRule.onAllNodesWithTag("reviewImage").fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 5000 // espera hasta 5 segundos
        )
        composeRule.onAllNodesWithTag("reviewImage").onFirst().performClick()

        // Ya se encuentra en la pantalla ReviewDetailScreen
        composeRule.onNodeWithTag("reviewCardTxtTitulo").assertTextEquals("caput varietas contego")
        composeRule.onNodeWithTag("reviewCardTxtDescripcion").assertTextEquals("Crudelis volubilis aufero adinventitias causa umquam conscendo acceptus.")
        composeRule.onAllNodesWithTag("reviewCardIconCalificacion").assertCountEquals(4)

        // Simula presionar el botón "Atrás" del sistema
        composeRule.activityRule.scenario.onActivity { activity -> activity.onBackPressedDispatcher.onBackPressed() }

        // Espera que haya regresado a la pantalla anterior
        composeRule.waitUntil(
            condition = { composeRule.onAllNodesWithTag("reviewsScreen").fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 3000
        )

        // Se vuelve a estar en la pantalla ReviewsScreen
        composeRule.onNodeWithTag("reviewsScreen").assertIsDisplayed()
        composeRule.waitUntil(
            condition = { composeRule.onAllNodesWithTag("reviewCardTxtLikeCount").fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 5000
        )
        composeRule.onAllNodesWithTag("reviewCardTxtLikeCount").onFirst().assertTextContains("0",true)
        composeRule.onAllNodesWithTag("reviewCardLikeButton").onFirst().performClick()

        // Se viaja a otra pagina para emular el cambio de pantalla
        composeRule.onAllNodesWithTag("reviewImage").onFirst().performClick()

        // Se vuelve atras y se verifica que el like haya aumentado en 1 unidad
        composeRule.activityRule.scenario.onActivity { activity -> activity.onBackPressedDispatcher.onBackPressed() }
        composeRule.waitUntil(
            condition = { composeRule.onAllNodesWithTag("reviewsScreen").fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 10000
        )
        composeRule.waitUntil(
            condition = { composeRule.onAllNodesWithTag("reviewCardTxtLikeCount").fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 5000
        )
        Thread.sleep(5000)
        composeRule.onAllNodesWithTag("reviewCardTxtLikeCount").onFirst().assertTextContains("1",true)

        // se quita el like y se verifica que haya disminuido en una unidad
        composeRule.onAllNodesWithTag("reviewCardLikeButton").onFirst().performClick()
        composeRule.waitUntil(
            condition = { composeRule.onAllNodesWithTag("reviewCardTxtLikeCount").fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 5000
        )
        Thread.sleep(5000)
        composeRule.onAllNodesWithTag("reviewCardTxtLikeCount").onFirst().assertTextContains("0",true)
    }
}