package fr.thomasbernard03.tarot.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.thomasbernard03.tarot.domain.models.PlayerColor

@Entity
data class PlayerEntity(
    @PrimaryKey
    val id : Long? = null,
    val name : String,
    val color : PlayerColor
)