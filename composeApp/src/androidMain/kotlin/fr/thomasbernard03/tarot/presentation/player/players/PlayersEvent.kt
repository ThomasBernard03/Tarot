package fr.thomasbernard03.tarot.presentation.player.players

import domain.models.PlayerColor
import domain.models.PlayerModel

sealed class PlayersEvent {
    data object OnLoadPlayers : PlayersEvent()

    data class OnPlayerSelected(val player: PlayerModel) : PlayersEvent()


    data object OnShowCreatePlayerDialog : PlayersEvent()
    data class OnCreatePlayerDialogNameChanged(val name: String) : PlayersEvent()
    data class OnCreatePlayerDialogColorSelected(val color: PlayerColor?) : PlayersEvent()
    data object OnDismissCreatePlayerDialog : PlayersEvent()
    data class OnCreatePlayerDialogValidated(val name: String, val color: PlayerColor) : PlayersEvent()


    data class OnDeletePlayer(val playerId: Long) : PlayersEvent()
    data class OnEditPlayer(val playerId : Long) : PlayersEvent()

    data object OnDismissMessage : PlayersEvent()
}