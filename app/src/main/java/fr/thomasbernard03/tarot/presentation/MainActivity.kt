package fr.thomasbernard03.tarot.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import fr.thomasbernard03.tarot.presentation.game.GameEvent
import fr.thomasbernard03.tarot.presentation.game.GameScreen
import fr.thomasbernard03.tarot.presentation.game.GameViewModel
import fr.thomasbernard03.tarot.presentation.history.HistoryScreen
import fr.thomasbernard03.tarot.presentation.history.HistoryViewModel
import fr.thomasbernard03.tarot.presentation.information.InformationScreen
import fr.thomasbernard03.tarot.presentation.round.RoundEvent
import fr.thomasbernard03.tarot.presentation.round.RoundScreen
import fr.thomasbernard03.tarot.presentation.round.RoundViewModel
import fr.thomasbernard03.tarot.presentation.theme.TarotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TarotTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "game"){
                        navigation(route = "game", startDestination = "current-game"){
                            composable(route = "current-game"){
                                val viewModel : GameViewModel = viewModel()
                                val state by viewModel.state.collectAsStateWithLifecycle()
                                GameScreen(state = state){ event ->

                                    if (event is GameEvent.OnNewRoundButtonPressed){
                                        navController.navigate("round")
                                    }

                                    viewModel.onEvent(event)
                                }
                            }
                            composable(route = "round"){
                                val viewModel : RoundViewModel = viewModel()
                                val state by viewModel.state.collectAsStateWithLifecycle()
                                RoundScreen(state = state){ event ->
                                    if (event is RoundEvent.OnGoBack)
                                        navController.navigateUp()

                                    viewModel.onEvent(event)
                                }
                            }
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