package com.example.postmatch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.postmatch.data.local.LocalReviewProvider
import com.example.postmatch.ui.AnalisisPartidoScreen
import com.example.postmatch.ui.ConfiguracionScreen
import com.example.postmatch.ui.FollowScreen
import com.example.postmatch.ui.login.LoginScreen
import com.example.postmatch.ui.notificaciones.NotificacionesScreen
import com.example.postmatch.ui.PerfilScreen
import com.example.postmatch.ui.RegistroScreen
import androidx.compose.material3.NavigationBar;
import androidx.compose.material3.NavigationBarItem;
import androidx.compose.material3.Icon;
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.postmatch.R
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postmatch.ui.PartidoScreen
import com.example.postmatch.ui.crearReview.CrearReviewScreen
import com.example.postmatch.ui.crearReview.CrearReviewViewModel
import com.example.postmatch.ui.login.LoginViewModel
import com.example.postmatch.ui.notificaciones.NotificacionesViewModel
import com.example.postmatch.ui.reviewDetail.ReviewDetailScreen
import com.example.postmatch.ui.reviewDetail.ReviewDetailViewModel
import com.example.postmatch.ui.reviews.ReviewsScreen
import com.example.postmatch.ui.reviews.ReviewsViewModel

sealed class Screen(val route: String) { // sealed class para rutas de las pantallas
    object Login : Screen(route = "login")
    object AnalisisPartido : Screen(route = "analisisPartido")
    object Configuracion : Screen(route = "configuracion")
    object Follow : Screen(route = "follow")
    object Notificaciones : Screen(route = "notificaciones")
    object Perfil : Screen(route = "perfil")
    object Reviews : Screen(route = "reviews")
    object Registro : Screen(route = "registro")
    object ReviewDetail : Screen(route = "reviewDetail/{idReview}")
    object CrearReview : Screen("crearReview")          // Nueva pantalla para el "más"
    object Partidos : Screen("partidos")
}

// Modelo de item
data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val contentDescription: String
)
@Composable
fun BottomNavBar(
    selectedRoute: String,
    onItemClick: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem(Screen.Reviews.route, Icons.Filled.Home, "Inicio"),
        BottomNavItem(Screen.Partidos.route, Icons.Filled.Search, "Buscar"),   // Cambiado
        BottomNavItem(Screen.CrearReview.route, Icons.Filled.AddBox, "Agregar"),    // Cambiado
        BottomNavItem(Screen.Notificaciones.route, Icons.Filled.Notifications, "Notificaciones"),
        BottomNavItem(Screen.Perfil.route, Icons.Filled.Person, "Perfil")

    )

    NavigationBar(

        containerColor = colorResource(id = R.color.verde_oscuro) // Fondo negro

    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedRoute == item.route,
                onClick = { onItemClick(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.contentDescription
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,      // Icono blanco al estar seleccionado
                    unselectedIconColor = Color.LightGray, // Icono gris cuando NO está seleccionado
                    indicatorColor = Color.Black           // El "fondo" del ítem seleccionado también negro
                )
            )
        }
    }
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

        composable(route= Screen.Partidos.route){
            PartidoScreen()
        }

        composable(route = Screen.Login.route) {
            val loginViewModel: LoginViewModel = viewModel()

            loginViewModel.setLoginButtonClick(action = { navController.navigate(Screen.Reviews.route) })
            loginViewModel.setSignUpButtonClick(action = { navController.navigate(Screen.Registro.route) })

            LoginScreen(
                loginViewModel = loginViewModel
            )
        }

        composable(route = Screen.Notificaciones.route) {
            val notificacionesViewModel: NotificacionesViewModel = viewModel()
            NotificacionesScreen(
                notificacionesViewModel = notificacionesViewModel
            )
        }

        composable(route = Screen.Perfil.route) {
            PerfilScreen(
                configuracionButtonClick = { navController.navigate(Screen.Configuracion.route) }
            )
        }

        composable(route = Screen.Reviews.route) {
            val reviewsViewModel: ReviewsViewModel = viewModel()

            reviewsViewModel.setReviewClick(action = { idReview ->
                navController.navigate(Screen.ReviewDetail.route.replace("{idReview}", "$idReview"))
            })

            ReviewsScreen(
                reviewsViewModel = reviewsViewModel
            )
        }

        composable(route = Screen.Registro.route) {
            RegistroScreen()
        }
        composable(
            route = Screen.ReviewDetail.route,
            arguments = listOf(navArgument("idReview") { type = NavType.IntType})
        ) {
            val reviewDetailViewModel: ReviewDetailViewModel = viewModel()
            val idReview = it.arguments?.getInt("idReview") ?: 0
            val reviewInfo = LocalReviewProvider.reviews.find {review -> review.idReview == idReview}
            if(reviewInfo != null) {
                reviewDetailViewModel.setReviewInfo(reviewInfo)
                reviewDetailViewModel.setComentarioButtonClick(action = { navController.navigate(Screen.Follow.route) })
                reviewDetailViewModel.setLikeButtonClick(action = { navController.navigate(Screen.AnalisisPartido.route) })
                ReviewDetailScreen(
                   reviewDetailViewModel = reviewDetailViewModel
                )
            } else navController.navigate(Screen.Reviews.route)
        }

        composable(Screen.CrearReview.route) {
            val crearReviewViewModel: CrearReviewViewModel = viewModel()

            CrearReviewScreen(
                crearReviewViewModel = crearReviewViewModel
            )
        }
        composable(Screen.Partidos.route) { PartidoScreen() }
    }
}

