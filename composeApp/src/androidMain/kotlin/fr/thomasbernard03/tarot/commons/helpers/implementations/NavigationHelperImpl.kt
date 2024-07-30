package fr.thomasbernard03.tarot.commons.helpers.implementations

import domain.models.Screen
import fr.thomasbernard03.tarot.commons.helpers.NavigationHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NavigationHelperImpl : NavigationHelper {

    private val _sharedFlow = MutableSharedFlow<NavigationHelper.NavigationEvent>(extraBufferCapacity = 1)
    override val sharedFlow = _sharedFlow.asSharedFlow()

    override fun navigateTo(screen: Screen, popupTo : Screen?) {
        _sharedFlow.tryEmit(NavigationHelper.NavigationEvent.NavigateTo(screen, popupTo))
    }

    override fun goBack() {
        _sharedFlow.tryEmit(NavigationHelper.NavigationEvent.GoBack)
    }
}