package data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import domain.models.Oudler

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RoundEntity::class,
            parentColumns = ["id"],
            childColumns = ["roundId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["roundId", "oudler"]
)
data class RoundOudlerEntity(
    val roundId : Long,
    val oudler : Oudler
)