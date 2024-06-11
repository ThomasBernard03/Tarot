package fr.thomasbernard03.tarot.domain.models

import java.util.Date

data class Game(
    val id: Long,
    val startedAt : Date,
    val finishedAt : Date? = null,
    val players: List<Player>
)