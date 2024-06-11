package fr.thomasbernard03.tarot.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.thomasbernard03.tarot.presentation.components.BottomAppBar
import fr.thomasbernard03.tarot.presentation.game.GameScreen
import fr.thomasbernard03.tarot.presentation.game.GameViewModel
import fr.thomasbernard03.tarot.presentation.history.HistoryScreen
import fr.thomasbernard03.tarot.presentation.history.HistoryViewModel
import fr.thomasbernard03.tarot.presentation.information.InformationScreen
import fr.thomasbernard03.tarot.presentation.theme.TarotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TarotTheme {
                val navController = rememberNavController()

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
                                val viewModel : GameViewModel = viewModel()
                                val state by viewModel.state.collectAsStateWithLifecycle()
                                GameScreen(state = state, onEvent = viewModel::onEvent)
                            }
                            composable("history"){
                                val viewModel : HistoryViewModel = viewModel()
                                val state by viewModel.state.collectAsStateWithLifecycle()
                                HistoryScreen(state = state, onEvent = viewModel::onEvent)
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