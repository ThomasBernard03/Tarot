package fr.thomasbernard03.tarot.presentation.player.players

import fr.thomasbernard03.tarot.domain.models.PlayerModel

sealed class PlayersEvent {
    data object OnLoadPlayers : PlayersEvent()

    data class OnPlayerSelected(val player: PlayerModel) : PlayersEvent()
}