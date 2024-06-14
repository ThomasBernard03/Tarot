package fr.thomasbernard03.tarot.domain.models

import java.util.Date

data class GameModel(
    val id: Long,
    val startedAt : Date,
    val finishedAt : Date? = null,
    val players: List<PlayerModel>
)