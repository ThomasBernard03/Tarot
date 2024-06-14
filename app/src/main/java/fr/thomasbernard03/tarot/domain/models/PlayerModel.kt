package fr.thomasbernard03.tarot.domain.models

data class PlayerModel(
    val id: Long,
    val name: String,
    val color : PlayerColor
)