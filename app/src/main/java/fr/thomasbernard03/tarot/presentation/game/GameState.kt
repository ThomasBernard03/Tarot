package fr.thomasbernard03.tarot.presentation.game

import fr.thomasbernard03.tarot.domain.models.Player

data class GameState(
    val loadingGame: Boolean = false,

    val showCreateGameSheet : Boolean = false,

    val players : List<Player> = emptyList(),
)