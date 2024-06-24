package fr.thomasbernard03.tarot.domain.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, val icon: ImageVector?, var title: String) {
    data object Game : NavigationItem("game", Icons.Rounded.Home, "Home")
    data object History : NavigationItem("history", Icons.AutoMirrored.Rounded.List, "History")
    data object Information : NavigationItem("information", Icons.Rounded.Info, "Information")
    data object Players : NavigationItem("players", Icons.Rounded.Person, "Players")
}