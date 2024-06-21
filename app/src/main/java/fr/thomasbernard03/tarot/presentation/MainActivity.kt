package fr.thomasbernard03.tarot.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.thomasbernard03.tarot.commons.helpers.NavigationHelper
import fr.thomasbernard03.tarot.domain.models.Screen
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.java.KoinJavaComponent.get

class MainActivity(
    private val navigationHelper: NavigationHelper = get(NavigationHelper::class.java)
) : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TarotTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    LaunchedEffect(Unit) {
                        navigationHelper.sharedFlow.onEach { event ->
                            when(event){
                                is NavigationHelper.NavigationEvent.NavigateTo -> {
                                    navController.navigate(event.screen.route) {
                                        launchSingleTop = true
                                        event.popupTo?.let { popUpTo(it.route) }
                                    }
                                }
                                is NavigationHelper.NavigationEvent.GoBack -> {
                                    navController.navigateUp()
                                }
                            }
                        }.launchIn(this)
                    }

                    NavHost(navController = navController, startDestination = Screen.Game.route){
                        navigation(route = "game", startDestination = "current-game"){
                            composable(route = "current-game"){
                                val viewModel : GameViewModel = viewModel()
                                val state by viewModel.state.collectAsStateWithLifecycle()
                                GameScreen(state = state){ event ->

                                    if (event is GameEvent.OnNewRoundButtonPressed){
                                        navController.navigate("round/${event.gameId}")
                                    }

                                    viewModel.onEvent(event)
                                }
                            }
                            composable(
                                route = Screen.Round.PATH,
                                arguments = listOf(
                                    navArgument(name = "gameId"){
                                        type = NavType.LongType
                                    }
                                )
                            ){
                                val gameId = it.arguments?.getLong("gameId") ?: 0
                                val viewModel : RoundViewModel = viewModel()
                                val state by viewModel.state.collectAsStateWithLifecycle()
                                RoundScreen(gameId = gameId, state = state){ event ->
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