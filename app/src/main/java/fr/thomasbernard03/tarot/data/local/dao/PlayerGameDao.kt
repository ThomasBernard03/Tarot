package fr.thomasbernard03.tarot.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import fr.thomasbernard03.tarot.data.local.entities.PlayerGameEntity

@Dao
interface PlayerGameDao {
    @Insert
    suspend fun addPlayerToGame(playerGameEntity: PlayerGameEntity)
}