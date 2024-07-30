package fr.thomasbernard03.tarot.presentation.player.players

import domain.models.PlayerColor
import domain.models.PlayerModel

data class PlayersState (
    val loadingPlayers : Boolean = false,
    val players : List<PlayerModel> = emptyList(),


    val showCreatePlayerDialog : Boolean = false,
    val createPlayerDialogName : String = "",
    val createPlayerDialogColor : PlayerColor? = null,
    val createPlayerDialogLoading : Boolean = false,
    val createPlayerDialogMessage: String = "",

    val message : String = "",
)