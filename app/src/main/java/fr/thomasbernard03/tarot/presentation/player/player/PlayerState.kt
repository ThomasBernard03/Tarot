package fr.thomasbernard03.tarot.presentation.player.player

import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.domain.models.PlayerModel

data class PlayerState(
    val loadingPlayer : Boolean = false,
    val player : PlayerModel? = null,

    val playerName : String = "",
    val playerColor : PlayerColor? = null,
)