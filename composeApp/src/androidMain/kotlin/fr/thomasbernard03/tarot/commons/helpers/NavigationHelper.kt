package fr.thomasbernard03.tarot.commons.helpers

import domain.models.Screen
import kotlinx.coroutines.flow.SharedFlow

interface NavigationHelper {
    val sharedFlow: SharedFlow<NavigationEvent>

    fun navigateTo(screen: Screen, popupTo: Screen? = null)

    fun goBack()

    sealed class NavigationEvent {
        data class NavigateTo(val screen: Screen, val popupTo: Screen? = null) : NavigationEvent()
        data object GoBack : NavigationEvent()
    }
}