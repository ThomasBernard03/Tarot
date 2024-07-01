package fr.thomasbernard03.tarot.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import fr.thomasbernard03.tarot.presentation.components.BottomAppBar
import fr.thomasbernard03.tarot.presentation.game.GameEvent
import fr.thomasbernard03.tarot.presentation.game.GameScreen
import fr.thomasbernard03.tarot.presentation.game.GameViewModel
import fr.thomasbernard03.tarot.presentation.history.HistoryScreen
import fr.thomasbernard03.tarot.presentation.history.HistoryViewModel
import fr.thomasbernard03.tarot.presentation.information.InformationScreen
import fr.thomasbernard03.tarot.presentation.player.players.PlayersScreen
import fr.thomasbernard03.tarot.presentation.player.players.PlayersViewModel
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