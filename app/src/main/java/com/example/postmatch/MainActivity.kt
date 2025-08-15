package com.example.postmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.postmatch.ui.ConfiguracionScreen
import com.example.postmatch.ui.LoginScreen
import com.example.postmatch.ui.ReviewScreen
import com.example.postmatch.ui.PublicacionesScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
    //       ConfiguracionScreen()
    //       LoginScreen()
    //       ReviewScreen()
    //       PublicacionesScreen()
        }
    }
}
