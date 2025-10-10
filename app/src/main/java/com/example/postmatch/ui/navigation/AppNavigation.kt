package com.example.postmatch.ui.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.postmatch.data.local.LocalReviewProvider
import com.example.postmatch.ui.Screens.follow.FollowScreen
import com.example.postmatch.ui.Screens.login.LoginScreen
import com.example.postmatch.ui.Screens.notificaciones.NotificacionesScreen
import com.example.postmatch.ui.Screens.perfil.PerfilScreen
import com.example.postmatch.ui.Screens.registro.RegistroScreen
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
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.activity
import com.example.postmatch.data.local.LocalPartidoProvider
import com.example.postmatch.ui.Screens.partidoDetail.PartidoDetailScreen
import com.example.postmatch.ui.Screens.partidoDetail.PartidoDetailViewModel
import com.example.postmatch.ui.Screens.Buscador.BuscadorScreenContent
import com.example.postmatch.ui.Screens.Buscador.BuscarViewModel
import com.example.postmatch.ui.Screens.actualizarReview.ActualizarReviewScreen
import com.example.postmatch.ui.Screens.actualizarReview.ActualizarReviewViewModel
import com.example.postmatch.ui.Screens.partidos.PartidoScreen
import com.example.postmatch.ui.Screens.configuracionPerfil.ConfiguracionPerfilScreen
import com.example.postmatch.ui.Screens.configuracionPerfil.ConfiguracionPerfilViewModel
import com.example.postmatch.ui.Screens.crearReview.CrearReviewScreen
import com.example.postmatch.ui.Screens.crearReview.CrearReviewViewModel
import com.example.postmatch.ui.Screens.follow.FollowViewModel
import com.example.postmatch.ui.Screens.login.LoginViewModel
import com.example.postmatch.ui.Screens.modificarPerfil.ModificarPerfilScreen
import com.example.postmatch.ui.Screens.modificarPerfil.ModificarPerfilViewModel
import com.example.postmatch.ui.Screens.notificaciones.NotificacionesViewModel
import com.example.postmatch.ui.Screens.partidos.PartidosViewModel
import com.example.postmatch.ui.Screens.perfil.PerfilViewModel
import com.example.postmatch.ui.Screens.registro.RegistroViewModel
import com.example.postmatch.ui.Screens.reviewDetail.ReviewDetailScreen
import com.example.postmatch.ui.Screens.reviewDetail.ReviewDetailViewModel
import com.example.postmatch.ui.Screens.reviews.ReviewsScreen
import com.example.postmatch.ui.Screens.reviews.ReviewsViewModel
import com.example.postmatch.ui.Screens.splash.SplashScreen
import com.google.firebase.auth.FirebaseAuth

sealed class Screen(val route: String) { // sealed class para rutas de las pantallas
    object Login : Screen(route = "login")
    object Splash : Screen("splash")
    object PartidoDetail : Screen(route = "partidoDetail/{idPartido}")
    object ConfiguracionPerfil : Screen(route = "configuracionPerfil")
    object Follow : Screen(route = "follow")

    object  ModificarPerfil : Screen(route = "ModificarPerfil")
    object Notificaciones : Screen(route = "notificaciones")
    object Perfil : Screen(route = "perfil")
    object Reviews : Screen(route = "reviews")
    object Registro : Screen(route = "registro")
    object ReviewDetail : Screen(route = "reviewDetail/{idReview}")

    object ActualizarReview: Screen(route = "actualizarReview/{idReview}")
    object CrearReview : Screen("crearReview/{idPartido}")          // Nueva pantalla para el "más"
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
        //BottomNavItem(Screen.CrearReview.route, Icons.Filled.AddBox, "Agregar"),    // Cambiado
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
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {


        composable(route = Screen.Splash.route) {
            val logged = hiltViewModel<LoginViewModel>().currentUser != null
            SplashScreen(
                onSplashFinished = {
                    if (logged) {
                        navController.navigate(Screen.Reviews.route) { // Va directo al home
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.Login.route) { // Si no, login
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                }
            )
        }
        composable(
            route = Screen.PartidoDetail.route,
            arguments = listOf(navArgument("idPartido") { type = NavType.IntType })
        ) {
            val partidoDetailViewModel: PartidoDetailViewModel = hiltViewModel()
            PartidoDetailScreen(
                partidoDetailViewModel = partidoDetailViewModel,
                idPartido = it.arguments?.getInt("idPartido") ?: 1
            )
        }




        composable(route = Screen.ConfiguracionPerfil.route) {

            val configuracionPerfilViewModel: ConfiguracionPerfilViewModel = hiltViewModel()
            val context = LocalContext.current
            val activity = context as? ComponentActivity

            // 🔹 Obtener el usuario autenticado actual
            val currentUser = FirebaseAuth.getInstance().currentUser
            val userId: String = currentUser?.uid ?: ""

            // 🔹 Manejo de logout
            configuracionPerfilViewModel.setOnLogout {
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            }

            // 🔹 Pasar el userId a la pantalla (si la necesita)
            ConfiguracionPerfilScreen(
                configuracionPerfilViewModel = configuracionPerfilViewModel,
                userId = userId,  // <-- agregas este parámetro
                onModificarPerfilClick = { navController.navigate(Screen.ModificarPerfil.route) }

            )
        }

        composable(route = Screen.Follow.route) {
            val followViewModel: FollowViewModel = hiltViewModel()
            FollowScreen(
                onFollowButtonChange = {},
                followViewModel = followViewModel
            )
        }

        composable(route = Screen.Partidos.route) {
            val partidoViewModel: PartidosViewModel = hiltViewModel()
            PartidoScreen(
                partidoViewModel = partidoViewModel,
                onReviewPartidoClick =  { idPartido ->
                    navController.navigate(
                        Screen.CrearReview.route.replace(
                            "{idPartido}",
                            "$idPartido"
                        )  ///fgggggg 2
                    )
                },
                onPartidoClick = { idPartido ->
                    navController.navigate(
                        Screen.PartidoDetail.route.replace(
                            "{idPartido}",
                            "$idPartido"
                        )
                    )
                }
            )
        }



        composable(route = Screen.Buscador.route) {

            val buscarViewModel: BuscarViewModel = hiltViewModel()
            val uiState by buscarViewModel.uiState.collectAsState()
            val context = LocalContext.current
            val activity = context as? ComponentActivity

            // Manejo del botón atrás
            BackHandler {
                activity?.finish()  // 🔹 Cierra la Activity → termina la app
            }


            BuscadorScreenContent(
                viewModel = buscarViewModel,
                onDestacadosClick = { navController.navigate(Screen.Partidos.route) }
            )
        }


        composable(route = Screen.Login.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            loginViewModel.setLoginButtonClick(callback = { navController.navigate(Screen.Reviews.route) })
            loginViewModel.setSignInButtonClick(callback = { navController.navigate(Screen.Registro.route) })
            LoginScreen(
                loginViewModel = loginViewModel
            )
        }

        composable(route = Screen.Notificaciones.route) {
            val notificacionesViewModel: NotificacionesViewModel = hiltViewModel()
            val context = LocalContext.current
            val activity = context as? ComponentActivity

            // Manejo del botón atrás
            BackHandler {
                activity?.finish()  // 🔹 Cierra la Activity → termina la app
            }

            NotificacionesScreen(
                notificacionesViewModel = notificacionesViewModel
            )
        }



        composable(route = Screen.Perfil.route) {
            val perfilViewModel: PerfilViewModel = hiltViewModel()
            val context = LocalContext.current
            val currentUser = FirebaseAuth.getInstance().currentUser
            val userId: String = currentUser?.uid ?: ""
            val activity = context as? ComponentActivity

            // Manejo del botón atrás
            BackHandler {
                activity?.finish()  // 🔹 Cierra la Activity → termina la app
            }

            PerfilScreen(
                configuracionButtonClick = { navController.navigate(Screen.ConfiguracionPerfil.route) },
                reviewButtonClick = {
                    navController.navigate(Screen.Reviews.route) {
                        launchSingleTop = true
                        popUpTo(Screen.Perfil.route) { inclusive = true }
                    }
                },
                perfilViewModel = perfilViewModel,
                idPerfilUsuario = userId,
                onReviewClick = { idReview: String ->
                    // Capturamos el id de la reseña y navegamos a la pantalla de edición
                    navController.navigate(
                        Screen.ActualizarReview.route.replace("{idReview}", idReview)
                    )
                }
            )
        }


        composable(route = Screen.Reviews.route) {
            val reviewsViewModel: ReviewsViewModel = hiltViewModel()
            val context = LocalContext.current
            val activity = context as? ComponentActivity

            // Manejo del botón atrás
            BackHandler {
                activity?.finish()  // 🔹 Cierra la Activity → termina la app
            }



            ReviewsScreen(
                reviewsViewModel = reviewsViewModel,
                onReviewClick = { idReview ->
                    navController.navigate(
                        Screen.ReviewDetail.route.replace(
                            "{idReview}",
                            "$idReview"
                        )
                    )
                }
            )
        }

        composable(route = Screen.Registro.route) {
            val registroViewModel: RegistroViewModel = hiltViewModel()

            registroViewModel.setOnRegisterSuccess {
                navController.navigate(Screen.Reviews.route)
            }

            RegistroScreen(
                registroViewModel = registroViewModel,
                onSuccess = { navController.navigate(Screen.Reviews.route)}
            )
        }
        composable(
            route = Screen.ReviewDetail.route,
            arguments = listOf(navArgument("idReview") { type = NavType.IntType })
        ) {
            val reviewDetailViewModel: ReviewDetailViewModel = hiltViewModel()
            ReviewDetailScreen(
                reviewDetailViewModel = reviewDetailViewModel,
                comentarioButtonClick = { navController.navigate(Screen.Follow.route) },
                likeButtonClick = { navController.navigate(Screen.PartidoDetail.route) }, // TODO: eliminar esta accion no correspondiente
                idReview = it.arguments?.getInt("idReview") ?: 0
            )
        }

        composable(
            route = Screen.CrearReview.route,
            arguments = listOf(navArgument("idPartido") { type = NavType.IntType })
        ) {
            val crearReviewViewModel: CrearReviewViewModel = hiltViewModel()
            val context = LocalContext.current
            val activity = context as? ComponentActivity

            BackHandler {
                activity?.finish()
            }

            CrearReviewScreen(
                crearReviewViewModel = crearReviewViewModel,
                reviewPartidoId = it.arguments?.getInt("idPartido") ?: 1,
                onReviewCreated = {
                    navController.navigate(Screen.Reviews.route) {
                        popUpTo(Screen.CrearReview.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.ActualizarReview.route,
            arguments = listOf(navArgument("idReview") { type = NavType.IntType })
        ) { backStackEntry ->
            val actualizarReviewViewModel: ActualizarReviewViewModel = hiltViewModel()
            val idReview = backStackEntry.arguments?.getInt("idReview") ?: 1

            ActualizarReviewScreen(
                actualizarReviewViewModel = actualizarReviewViewModel,
                reviewId = idReview,
                onReviewUpdated = { navController.popBackStack() },
            )
        }

        composable(route = Screen.ModificarPerfil.route) {
            val modificarPerfilViewModel: ModificarPerfilViewModel = hiltViewModel()
            val currentUser = FirebaseAuth.getInstance().currentUser
            val userId: String = currentUser?.uid ?: ""

            ModificarPerfilScreen(
                modificarPerfilViewModel = modificarPerfilViewModel,
                UserId = userId,
                onSuccess = { navController.navigate(Screen.Reviews.route)}

            )
        }










    }
}

