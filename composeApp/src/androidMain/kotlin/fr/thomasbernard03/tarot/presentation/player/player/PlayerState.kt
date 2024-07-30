package fr.thomasbernard03.tarot.presentation.player.player

import domain.models.PlayerModel

data class PlayerState(
    val loadingPlayer : Boolean = false,
    val player : PlayerModel? = null,
    val message : String = "",
)