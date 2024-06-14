package fr.thomasbernard03.tarot.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import fr.thomasbernard03.tarot.domain.models.Bid

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RoundEntity::class,
            parentColumns = ["id"],
            childColumns = ["roundId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["playerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
)
data class TakerEntity(
    @PrimaryKey
    val id : Long? = null,
    val roundId : Long,
    val playerId : Long,
    val bid : Bid,
    val score : Int  // Between 0 and 91
)