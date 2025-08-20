package com.example.postmatch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.postmatch.ui.AnalisisPartidoScreen
import com.example.postmatch.ui.ConfiguracionScreen
import com.example.postmatch.ui.FollowScreen
import com.example.postmatch.ui.LoginScreen
import com.example.postmatch.ui.NotificacionesScreen
import com.example.postmatch.ui.PerfilScreen
import com.example.postmatch.ui.PublicacionesScreen
import com.example.postmatch.ui.RegistroScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable(route = "analisisPartido") {
            AnalisisPartidoScreen()
        }

        composable(route = "configuracion") {
            ConfiguracionScreen()
        }

        composable(route = "follow") {
            FollowScreen()
        }

        composable(route = "login") {
            LoginScreen(
                loginButtonClick = { navController.navigate("publicaciones") },
                signUpButtonClick = { navController.navigate("registro") }
            )
        }

        composable(route = "notificaciones") {
            NotificacionesScreen()
        }

        composable(route = "perfil") {
            PerfilScreen()
        }

        composable(route = "publicaciones") {
            PublicacionesScreen(
                notificacionesButtonClick = { navController.navigate("notificaciones") },
                settingsButtonClick = { navController.navigate("configuracion") }
            )
        }

        composable(route = "registro") {
            RegistroScreen()
        }
    }
}