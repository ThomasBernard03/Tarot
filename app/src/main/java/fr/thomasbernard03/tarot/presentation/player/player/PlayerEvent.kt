package fr.thomasbernard03.tarot.presentation.player.player

import fr.thomasbernard03.tarot.domain.models.PlayerColor

sealed class PlayerEvent {
    data object OnDismissMessage : PlayerEvent()
    data object OnGoBack : PlayerEvent()
    data class OnLoadPlayer(val id : Long) : PlayerEvent()

    data class OnDeletePlayer(val id : Long) : PlayerEvent()
    data class OnEditPlayer(val id : Long, val name : String, val color : PlayerColor) : PlayerEvent()
}