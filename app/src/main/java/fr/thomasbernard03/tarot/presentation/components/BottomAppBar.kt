package fr.thomasbernard03.tarot.presentation.components

import android.content.res.Configuration
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fr.thomasbernard03.tarot.domain.models.NavigationItem

@Composable
fun BottomAppBar(navController: NavController) {
    androidx.compose.material3.BottomAppBar {
        val items = listOf(
            NavigationItem.Game,
            NavigationItem.History,
            NavigationItem.Information,
        )
        var selectedItem by remember { mutableIntStateOf(0) }
        var currentRoute by remember { mutableStateOf(NavigationItem.Game.route) }

        items.forEachIndexed { index, navigationItem ->
            if (navigationItem.route == currentRoute) {
                selectedItem = index
            }
        }

        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    alwaysShowLabel = true,
                    icon = { Icon(item.icon!!, contentDescription = item.title) },
                    label = { Text(item.title) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        currentRoute = item.route
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun BottomAppBarPreview() = PreviewComponent {
    val navController = rememberNavController()
    BottomAppBar(navController = navController)
}