package fr.thomasbernard03.tarot.presentation.game

import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel

sealed class GameEvent {
    data object OnGetCurrentGame : GameEvent()

    // Create game sheet
    data object OnOpenCreateDialogSheet : GameEvent()
    data object OnCloseCreateDialogSheet : GameEvent()
    data class OnValidateCreateGameSheet(val players : List<CreatePlayerModel>) : GameEvent()

    data object OnNewRoundButtonPressed : GameEvent()
}