package fr.thomasbernard03.tarot.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import fr.thomasbernard03.tarot.domain.models.Oudler

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = TakerEntity::class,
            parentColumns = ["id"],
            childColumns = ["takerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["takerId", "oudler"]
)
data class TakerOudlerEntity(
    val takerId : Long,
    val oudler : Oudler
)