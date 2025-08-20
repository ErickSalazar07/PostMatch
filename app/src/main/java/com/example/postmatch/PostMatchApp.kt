package com.example.postmatch

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.postmatch.navigation.AppNavigation
import com.example.postmatch.ui.ReviewScreen

@Composable
fun PostMatchApp() {
    Scaffold (){
        val navController = rememberNavController()
        AppNavigation(
            navController = navController,
            modifier = Modifier.padding(it)
        )
    }
}