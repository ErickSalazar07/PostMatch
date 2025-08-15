package com.example.postmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.postmatch.ui.AnalisisPartidoScreen
import com.example.postmatch.ui.ConfiguracionScreen
import com.example.postmatch.ui.FollowScreen
import com.example.postmatch.ui.LoginScreen
import com.example.postmatch.ui.NotificacionesScreen
import com.example.postmatch.ui.PerfilScreen
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
                    // LoginScreen(modifier = Modifier.padding(it))
                    ReviewScreen(modifier = Modifier.padding(it))
                    // ConfiguracionScreen(modifier = Modifier.padding(it))
                    // PublicacionesScreen(modifier = Modifier.padding(it))
                    // AnalisisPartidoScreen(modifier = Modifier.padding(it))
                    // FollowScreen(modifier = Modifier.padding(it))
                    // NotificacionesScreen(modifier = Modifier.padding(it))
                    // PerfilScreen(modifier = Modifier.padding(it))
                }
            }
        }
    }
}

/*@Composable
fun PostMatchTopBa(){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "PostMatch",
                fontWeight = FontWeight.Bold
                style = MaterialTheme.typography
            )
        }
    )
}
*/
