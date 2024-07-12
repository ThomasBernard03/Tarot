package data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import domain.models.PlayerColor

@Entity(
    indices = [Index(value = ["name"], unique = true)]
)
data class PlayerEntity(
    @PrimaryKey
    val id : Long? = null,
    val name : String,
    val color : PlayerColor
)