package fr.thomasbernard03.tarot.presentation.game

import fr.thomasbernard03.tarot.domain.models.Game
import fr.thomasbernard03.tarot.domain.models.Player

data class GameState(
    val loadingGame: Boolean = false,
    val currentGame : Game? = null,

    val showCreateGameSheet : Boolean = false,
)