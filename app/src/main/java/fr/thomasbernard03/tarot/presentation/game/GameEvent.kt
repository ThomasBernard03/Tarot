package fr.thomasbernard03.tarot.presentation.game

import fr.thomasbernard03.tarot.domain.models.Bid
import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.Oudler
import fr.thomasbernard03.tarot.domain.models.PlayerModel

sealed class GameEvent {
    data object OnGetCurrentGame : GameEvent()

    // Create game sheet
    data object OnOpenCreateDialogSheet : GameEvent()
    data object OnCloseCreateDialogSheet : GameEvent()
    data class OnValidateCreateGameSheet(val players : List<CreatePlayerModel>) : GameEvent()


    data object OnOpenNewRoundSheet : GameEvent()
    data object OnCloseNewRoundSheet : GameEvent()
    data class OnValidateCreateRoundSheet(val taker : PlayerModel, val bid : Bid, val oudlers : List<Oudler>, val numberOfPoints : Int) : GameEvent()
}