package fr.thomasbernard03.tarot.presentation.game

import domain.models.GameModel
import domain.models.PlayerModel

data class GameState(
    val loadingGame: Boolean = true,
    val currentGame : GameModel? = null,

    val showCreateGameSheet : Boolean = false,
    val players : List<PlayerModel> = emptyList(),
    val loadingPlayers : Boolean = false,

    val message : String = ""
)