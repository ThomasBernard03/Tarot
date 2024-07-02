package fr.thomasbernard03.tarot.presentation.game

import fr.thomasbernard03.tarot.domain.models.GameModel
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.errors.DeleteRoundError

data class GameState(
    val loadingGame: Boolean = true,
    val currentGame : GameModel? = null,

    val showCreateGameSheet : Boolean = false,
    val players : List<PlayerModel> = emptyList(),
    val loadingPlayers : Boolean = false,

    val message : String = ""
)