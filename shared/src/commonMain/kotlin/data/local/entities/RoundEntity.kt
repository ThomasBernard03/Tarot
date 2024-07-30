package data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import domain.models.Bid
import kotlinx.datetime.LocalDateTime

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["takerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["calledPlayerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["gameId"]),
        Index(value = ["takerId"]),
        Index(value = ["calledPlayerId"])
    ]
)
data class RoundEntity(
    @PrimaryKey
    val id : Long? = null,
    val gameId: Long,
    val finishedAt : LocalDateTime,
    val bid : Bid,
    val points : Int,
    val takerId : Long,
    val calledPlayerId : Long?
)