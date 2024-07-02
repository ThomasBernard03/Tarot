package fr.thomasbernard03.tarot.presentation.game

import fr.thomasbernard03.tarot.domain.models.PlayerModel

sealed class GameEvent {
    data object OnGetCurrentGame : GameEvent()
    data class OnFinishGame(val gameId : Long) : GameEvent()

    // Create game sheet
    data object OnOpenCreateDialogSheet : GameEvent()
    data object OnCloseCreateDialogSheet : GameEvent()
    data class OnValidateCreateGameSheet(val players : List<PlayerModel>) : GameEvent()

    data class OnNewRoundButtonPressed(val gameId : Long) : GameEvent()

    data class OnDeleteRound(val roundId : Long) : GameEvent()


    data object OnDismissMessage : GameEvent()
}