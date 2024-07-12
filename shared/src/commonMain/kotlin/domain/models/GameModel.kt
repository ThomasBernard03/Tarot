package domain.models

import kotlinx.datetime.LocalDateTime

data class GameModel(
    val id: Long,
    val startedAt : LocalDateTime,
    val finishedAt : LocalDateTime? = null,
    val players: List<PlayerModel>,
    val rounds : List<RoundModel>
)