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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import domain.models.Screen
import fr.thomasbernard03.tarot.commons.helpers.NavigationHelper
import fr.thomasbernard03.tarot.presentation.components.BottomAppBar
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

                navController.addOnDestinationChangedListener { _, destination, _ ->
                    Log.i("Navigation", "Navigating to : ${destination.route}")
                }

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