package fr.thomasbernard03.tarot.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = ["playerId", "gameId"],
    foreignKeys = [
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["playerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.CASCADE
        )

    ],
    indices = [
        Index(value = ["playerId"]),
        Index(value = ["gameId"])
    ]
)
data class PlayerGameEntity(
    val playerId: Long,
    val gameId: Long
)