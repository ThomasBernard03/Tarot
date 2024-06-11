package fr.thomasbernard03.tarot.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameEntity(
    @PrimaryKey
    val id: Long? = null,
    val startedAt: Long,
    val finishedAt: Long? = null
)