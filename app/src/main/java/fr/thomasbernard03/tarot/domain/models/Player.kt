package fr.thomasbernard03.tarot.domain.models

data class Player(
    val id: Long,
    val name: String,
    val color : PlayerColor
)