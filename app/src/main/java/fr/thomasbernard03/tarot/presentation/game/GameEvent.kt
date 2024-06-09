package fr.thomasbernard03.tarot.presentation.game

import fr.thomasbernard03.tarot.domain.models.Player

sealed class GameEvent {

    // Create game sheet
    data object OnOpenCreateDialogSheet : GameEvent()
    data object OnCloseCreateDialogSheet : GameEvent()
    data class OnPlayersChanged(val players : List<Player>) : GameEvent()
    data object OnCreateNewPlayer : GameEvent()
    data class OnValidateCreateGameSheet(val players : List<Player>) : GameEvent()
}