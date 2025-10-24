package com.example.postmatch

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.postmatch.ui.navigation.BottomNavBar
import com.example.postmatch.ui.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.messaging.FirebaseMessaging


@Composable
fun PostMatchApp() {

    FirebaseMessaging.getInstance().token.addOnCompleteListener{ task->
        if(task.isSuccessful){
            Log.d("Token", task.result)
        } else {
            Log.d("Token", "Error getting token")
        }
    }

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route
    val currentUser = FirebaseAuth.getInstance().currentUser
    val photoUrl: String = currentUser?.photoUrl?.toString() ?: ""
    val showBottomBar = currentDestination !in listOf("login", "registro","splash")
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val userId: String = currentUser?.uid ?: ""

    val context = LocalContext.current

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                Log.d("NotificationPermission", "permission granted")
            } else{
                Log.d("NotificationPermission", "Permission denied")
            }
        }
    )

    LaunchedEffect(Unit) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

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






