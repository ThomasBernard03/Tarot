package fr.thomasbernard03.tarot.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import domain.models.Screen
import fr.thomasbernard03.tarot.presentation.components.BottomAppBar
import fr.thomasbernard03.tarot.presentation.theme.TarotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TarotTheme {
                val navController = rememberNavController()

                navController.addOnDestinationChangedListener { _, destination, _ ->
                    Log.i("Navigation", "Navigating to : ${destination.route}")
                }

                Scaffold(
                    bottomBar = { BottomAppBar(navController = navController) }
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize().padding(it),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(navController = navController, startDestination = Screen.Game.route){
                            navigationGraph(navController = navController)
                        }
                    }
                }
            }
        }
    }
}