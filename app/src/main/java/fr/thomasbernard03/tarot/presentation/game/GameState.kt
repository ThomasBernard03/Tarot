package fr.thomasbernard03.tarot.presentation.game

import fr.thomasbernard03.tarot.domain.models.GameModel

data class GameState(
    val loadingGame: Boolean = true,
    val currentGame : GameModel? = null,

    val showCreateGameSheet : Boolean = false,
)