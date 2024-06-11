package fr.thomasbernard03.tarot.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class GameEntity(
    @PrimaryKey
    val id: Long? = null,
    val startedAt: Date,
    val finishedAt: Date? = null
)