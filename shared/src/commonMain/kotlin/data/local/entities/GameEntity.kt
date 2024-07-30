package data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity
data class GameEntity(
    @PrimaryKey
    val id: Long? = null,
    val startedAt: LocalDateTime,
    val finishedAt: LocalDateTime? = null
)