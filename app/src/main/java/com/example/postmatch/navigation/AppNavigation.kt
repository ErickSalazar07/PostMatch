package com.example.postmatch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.postmatch.data.local.LocalReviewProvider
import com.example.postmatch.ui.AnalisisPartidoScreen
import com.example.postmatch.ui.ConfiguracionScreen
import com.example.postmatch.ui.FollowScreen
import com.example.postmatch.ui.LoginScreen
import com.example.postmatch.ui.NotificacionesScreen
import com.example.postmatch.ui.PerfilScreen
import com.example.postmatch.ui.PublicacionesScreen
import com.example.postmatch.ui.RegistroScreen
import com.example.postmatch.ui.reusable.ReviewDetail

sealed class Screen(val route: String) { // sealed class para rutas de las pantallas
    object Login : Screen(route = "login")
    object AnalisisPartido : Screen(route = "analisisPartido")
    object Configuracion : Screen(route = "configuracion")
    object Follow : Screen(route = "follow")
    object Notificaciones : Screen(route = "notificaciones")
    object Perfil : Screen(route = "perfil")
    object Publicaciones : Screen(route = "publicaciones")
    object Registro : Screen(route = "registro")
    object ReviewDetail : Screen(route = "reviewDetail")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        composable(route = Screen.AnalisisPartido.route) {
            AnalisisPartidoScreen()
        }

        composable(route = Screen.Configuracion.route) {
            ConfiguracionScreen()
        }

        composable(route = Screen.Follow.route) {
            FollowScreen()
        }

        composable(route = Screen.Login.route) {
            LoginScreen(
                loginButtonClick = {
                    navController.navigate(Screen.Publicaciones.route) {
                        popUpTo(id = 0) {
                            inclusive = true
                        }
                    }
                },
                signUpButtonClick = { navController.navigate(Screen.Registro.route) }
            )
        }

        composable(route = Screen.Notificaciones.route) {
            NotificacionesScreen()
        }

        composable(route = Screen.Perfil.route) {
            PerfilScreen()
        }

        composable(route = Screen.Publicaciones.route) {
            PublicacionesScreen(
                reviewClick = { idReview ->
                    navController.navigate("${Screen.ReviewDetail.route}/$idReview")
                }
            )
        }

        composable(route = Screen.Registro.route) {
            RegistroScreen()
        }

        composable(
            route = "${Screen.ReviewDetail.route}/{idReview}",
            arguments = listOf(navArgument("idReview") { type = NavType.IntType})
        ) {
            val idReview = it.arguments?.getInt("idReview") ?: 0
            val reviewInfo = LocalReviewProvider.reviews.find {review -> review.idReview == idReview}
            if(reviewInfo == null) navController.navigate(Screen.Publicaciones.route)

            ReviewDetail(
                reviewInfo = reviewInfo!!,
                comentarioButtonClick = { navController.navigate(Screen.Follow.route) },
                likeButtonClick = { navController.navigate(Screen.AnalisisPartido.route)}
            )
        }
    }
}