package fr.thomasbernard03.tarot.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.thomasbernard03.tarot.presentation.components.BottomAppBar
import fr.thomasbernard03.tarot.presentation.game.GameScreen
import fr.thomasbernard03.tarot.presentation.history.HistoryScreen
import fr.thomasbernard03.tarot.presentation.information.InformationScreen
import fr.thomasbernard03.tarot.presentation.theme.TarotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            TarotTheme {
                Scaffold(
                    bottomBar = { BottomAppBar(navController) }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(navController = navController, startDestination = "game"){
                            composable("game"){
                                GameScreen()
                            }
                            composable("history"){
                                HistoryScreen()
                            }
                            composable("information") {
                                InformationScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}