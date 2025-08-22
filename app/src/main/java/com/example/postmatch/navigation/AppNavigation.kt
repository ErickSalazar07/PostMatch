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
import androidx.compose.material3.NavigationBar;
import androidx.compose.material3.MaterialTheme;
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
import com.example.postmatch.ui.PartidoScreen
import com.example.postmatch.ui.ReviewScreen

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
    object Review : Screen("review")          // Nueva pantalla para el "más"
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
        BottomNavItem(Screen.Publicaciones.route, Icons.Filled.Home, "Inicio"),
        BottomNavItem(Screen.Partidos.route, Icons.Filled.Search, "Buscar"),   // Cambiado
        BottomNavItem(Screen.Review.route, Icons.Filled.AddBox, "Agregar"),    // Cambiado
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
            PerfilScreen(
                configuracionButtonClick = { navController.navigate(Screen.Configuracion.route) }
            )
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
        composable(Screen.Review.route) { ReviewScreen() }        // Agregado
        composable(Screen.Partidos.route) { PartidoScreen() }
    }
}

