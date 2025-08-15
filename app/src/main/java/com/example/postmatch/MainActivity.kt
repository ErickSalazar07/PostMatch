package com.example.postmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.postmatch.ui.ConfiguracionScreen
import com.example.postmatch.ui.LoginScreen
import com.example.postmatch.ui.ReviewScreen
import com.example.postmatch.ui.PublicacionesScreen
import com.example.postmatch.ui.theme.PostMatchTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PostMatchTheme {
                Scaffold (){
                    LoginScreen(
                        modifier = Modifier.padding(it)
                    )
                }
            }
        }
    }
}
