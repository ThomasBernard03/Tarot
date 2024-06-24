package fr.thomasbernard03.tarot.presentation.player.players

import fr.thomasbernard03.tarot.domain.models.PlayerModel

data class PlayersState (
    val loadingPlayers : Boolean = false,
    val players : List<PlayerModel> = emptyList(),
)