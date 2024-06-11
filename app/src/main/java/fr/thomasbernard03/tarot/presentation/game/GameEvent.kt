package fr.thomasbernard03.tarot.presentation.game

import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.Player

sealed class GameEvent {

    // Create game sheet
    data object OnOpenCreateDialogSheet : GameEvent()
    data object OnCloseCreateDialogSheet : GameEvent()
    data class OnValidateCreateGameSheet(val players : List<CreatePlayerModel>) : GameEvent()
}