package com.example.postmatch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.postmatch.data.local.LocalReviewProvider
import com.example.postmatch.ui.follow.FollowScreen
import com.example.postmatch.ui.login.LoginScreen
import com.example.postmatch.ui.notificaciones.NotificacionesScreen
import com.example.postmatch.ui.perfil.PerfilScreen
import com.example.postmatch.ui.registro.RegistroScreen
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postmatch.data.local.LocalPartidoProvider
import com.example.postmatch.ui.partidoDetail.PartidoDetailScreen
import com.example.postmatch.ui.partidoDetail.PartidoDetailViewModel
import com.example.postmatch.ui.Buscador.BuscadorScreenContent
import com.example.postmatch.ui.Buscador.BuscarViewModel
import com.example.postmatch.ui.partidos.PartidoScreen
import com.example.postmatch.ui.configuracionPerfil.ConfiguracionPerfilScreen
import com.example.postmatch.ui.configuracionPerfil.ConfiguracionPerfilViewModel
import com.example.postmatch.ui.crearReview.CrearReviewScreen
import com.example.postmatch.ui.crearReview.CrearReviewViewModel
import com.example.postmatch.ui.follow.FollowViewModel
import com.example.postmatch.ui.login.LoginViewModel
import com.example.postmatch.ui.notificaciones.NotificacionesViewModel
import com.example.postmatch.ui.partidos.PartidosViewModel
import com.example.postmatch.ui.registro.RegistroViewModel
import com.example.postmatch.ui.reviewDetail.ReviewDetailScreen
import com.example.postmatch.ui.reviewDetail.ReviewDetailViewModel
import com.example.postmatch.ui.reviews.ReviewsScreen
import com.example.postmatch.ui.reviews.ReviewsViewModel

sealed class Screen(val route: String) { // sealed class para rutas de las pantallas
    object Login : Screen(route = "login")
    object PartidoDetail : Screen(route = "partidoDetail/{idPartido}")
    object ConfiguracionPerfil : Screen(route = "configuracionPerfil")
    object Follow : Screen(route = "follow")
    object Notificaciones : Screen(route = "notificaciones")
    object Perfil : Screen(route = "perfil")
    object Reviews : Screen(route = "reviews")
    object Registro : Screen(route = "registro")
    object ReviewDetail : Screen(route = "reviewDetail/{idReview}")
    object CrearReview : Screen("crearReview")          // Nueva pantalla para el "más"
    object Partidos : Screen("partidos")

    object Buscador : Screen("buscador")

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
        BottomNavItem(Screen.Buscador.route, Icons.Filled.Search, "Buscar"),   // Cambiado
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
        composable(
            route = Screen.PartidoDetail.route,
            arguments = listOf(navArgument("idPartido") { type = NavType.IntType})
        ) {
            val partidoDetailViewModel: PartidoDetailViewModel = viewModel()
            val idPartido = it.arguments?.getInt("idPartido") ?: 0
            val partidoInfo = LocalPartidoProvider.partidos.find { partido -> partido.idPartido == idPartido}
            if(partidoInfo != null) {
                partidoDetailViewModel.setPartido(partidoInfo)
                PartidoDetailScreen(
                    partidoDetailViewModel = partidoDetailViewModel
                )
            } else navController.navigate(Screen.Partidos.route)
        }

        composable(route = Screen.ConfiguracionPerfil.route) {
            val configuracionPerfilViewModel: ConfiguracionPerfilViewModel = viewModel()
            ConfiguracionPerfilScreen(
                configuracionPerfilViewModel = configuracionPerfilViewModel
            )
        }

        composable(route = Screen.Follow.route) {
            val followViewModel: FollowViewModel = viewModel()
            FollowScreen(
                onFollowButtonChange = {},
                followViewModel = followViewModel
            )
        }

        composable(route= Screen.Partidos.route){
            val partidoViewModel: PartidosViewModel = viewModel()
            PartidoScreen(
                partidoViewModel = partidoViewModel,
                onPartidoClick = { idPartido -> navController.navigate(Screen.PartidoDetail.route.replace("{idPartido}", "$idPartido")) }
            )
        }

        composable(route = Screen.Buscador.route) {
            val buscarViewModel: BuscarViewModel = viewModel()
            val uiState by buscarViewModel.uiState.collectAsState()

            BuscadorScreenContent(
                state = uiState,
                onBuscar = { query -> buscarViewModel.onBuscar(query) }
            )
        }


        composable(route = Screen.Login.route) {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(
                loginViewModel = loginViewModel,
                loginButtonClick = { navController.navigate(Screen.Reviews.route) },
                signUpButtonClick = { navController.navigate(Screen.Registro.route) }
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
                configuracionButtonClick = { navController.navigate(Screen.ConfiguracionPerfil.route) }
            )
        }

        composable(route = Screen.Reviews.route) {
            val reviewsViewModel: ReviewsViewModel = viewModel()
            ReviewsScreen(
                reviewsViewModel = reviewsViewModel,
                onReviewClick = { idReview -> navController.navigate(Screen.ReviewDetail.route.replace("{idReview}", "$idReview")) }
            )
        }

        composable(route = Screen.Registro.route) {
            val registroViewModel: RegistroViewModel = viewModel()
            RegistroScreen(
                registroViewModel = registroViewModel
            )
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
                ReviewDetailScreen(
                   reviewDetailViewModel = reviewDetailViewModel,
                   comentarioButtonClick = { navController.navigate(Screen.Follow.route) },
                   likeButtonClick = { navController.navigate(Screen.PartidoDetail.route) } // TODO: eliminar esta accion no correspondiente
                )
            } else navController.navigate(Screen.Reviews.route)
        }

        composable(Screen.CrearReview.route) {
            val crearReviewViewModel: CrearReviewViewModel = viewModel()
            CrearReviewScreen(
                crearReviewViewModel = crearReviewViewModel
            )
        }
    }
}

