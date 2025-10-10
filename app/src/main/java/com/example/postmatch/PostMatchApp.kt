package com.example.postmatch

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.postmatch.ui.navigation.AppNavigation
import androidx.compose.runtime.*
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.postmatch.ui.navigation.BottomNavBar
import com.example.postmatch.ui.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


@Composable
fun PostMatchApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route
    val currentUser = FirebaseAuth.getInstance().currentUser
    val photoUrl: String = currentUser?.photoUrl?.toString() ?: ""
    val showBottomBar = currentDestination !in listOf("login", "registro","splash")
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val userId: String = currentUser?.uid ?: ""




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






