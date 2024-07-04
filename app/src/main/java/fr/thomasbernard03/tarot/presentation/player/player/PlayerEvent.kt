package fr.thomasbernard03.tarot.presentation.player.player

import fr.thomasbernard03.tarot.domain.models.PlayerColor

sealed class PlayerEvent {
    data object OnGoBack : PlayerEvent()
    data class OnLoadPlayer(val id : Long) : PlayerEvent()


    data class OnPlayerNameChanged(val name : String) : PlayerEvent()
    data class OnPlayerColorChanged(val color : PlayerColor?) : PlayerEvent()
}