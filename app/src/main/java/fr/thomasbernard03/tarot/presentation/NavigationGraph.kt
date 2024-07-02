package fr.thomasbernard03.tarot.presentation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import fr.thomasbernard03.tarot.domain.models.Screen
import fr.thomasbernard03.tarot.presentation.game.GameEvent
import fr.thomasbernard03.tarot.presentation.game.GameScreen
import fr.thomasbernard03.tarot.presentation.game.GameViewModel
import fr.thomasbernard03.tarot.presentation.history.HistoryScreen
import fr.thomasbernard03.tarot.presentation.history.HistoryViewModel
import fr.thomasbernard03.tarot.presentation.settings.InformationScreen
import fr.thomasbernard03.tarot.presentation.player.players.PlayersScreen
import fr.thomasbernard03.tarot.presentation.player.players.PlayersViewModel
import fr.thomasbernard03.tarot.presentation.round.RoundEvent
import fr.thomasbernard03.tarot.presentation.round.RoundScreen
import fr.thomasbernard03.tarot.presentation.round.RoundViewModel

fun NavGraphBuilder.navigationGraph(navController : NavController){
    navigation(route = "game", startDestination = "current-game"){
        composable(route = "current-game"){
            val viewModel : GameViewModel = viewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            GameScreen(state = state){ event ->

                if (event is GameEvent.OnNewRoundButtonPressed){
                    navController.navigate("round/${event.gameId}")
                }
                else if (event is GameEvent.OnGoToRoundDetail){
                    navController.navigate("round/${event.gameId}?roundId=${event.roundId}")
                }
                else if (event is GameEvent.OnEditRound){
                    navController.navigate("round/${event.gameId}?roundId=${event.roundId}&editable=true")
                }

                viewModel.onEvent(event)
            }
        }
        composable(
            route = Screen.Round.PATH,
            arguments = listOf(
                navArgument(name = "gameId"){
                    type = NavType.LongType
                },
                navArgument(name = "roundId"){
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(name = "editable"){
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ){
            val gameId = it.arguments?.getLong("gameId") ?: 0
            val roundId = it.arguments?.getString("roundId")?.let {
                if (it == "0")  null
                else it.toLong()
            }
            val editable = it.arguments?.getBoolean("editable") ?: false

            val viewModel : RoundViewModel = viewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            RoundScreen(
                gameId = gameId,
                roundId = roundId,
                editable = editable,
                state = state
            ){ event ->
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
    navigation(route = "players", startDestination = "players-list") {
        composable("players-list") {
            val viewModel : PlayersViewModel = viewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            PlayersScreen(state = state) { event ->
                viewModel.onEvent(event)
            }
        }
    }
}