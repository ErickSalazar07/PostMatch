package com.example.postmatch

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.postmatch.ui.navigation.AppNavigation
import androidx.compose.runtime.*
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.postmatch.ui.navigation.BottomNavBar


@Composable
fun PostMatchApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route
    val showBottomBar = currentDestination !in listOf("login", "registro","splash")


    Scaffold (
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    selectedRoute = currentDestination ?: "publicaciones",
                    onItemClick = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }

    ){

        AppNavigation(
            navController = navController,
            modifier = Modifier.padding(it)
        )
        }


}






