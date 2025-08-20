package com.example.postmatch

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.postmatch.ui.ReviewScreen

@Composable
fun PostMatchApp() {
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
