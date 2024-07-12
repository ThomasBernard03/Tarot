package domain.models

import domain.models.PlayerColor

data class PlayerModel(
    val id: Long,
    val name: String,
    val color : PlayerColor
)