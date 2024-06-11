package fr.thomasbernard03.tarot.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import fr.thomasbernard03.tarot.data.local.entities.PlayerEntity

@Dao
interface PlayerDao {
    @Insert
    suspend fun insertPlayers(players: List<PlayerEntity>): List<Long>
}