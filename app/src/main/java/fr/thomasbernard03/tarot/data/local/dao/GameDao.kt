package fr.thomasbernard03.tarot.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.thomasbernard03.tarot.data.local.entities.GameEntity

@Dao
interface GameDao {
    @Insert
    suspend fun createGame(gameEntity: GameEntity): Long

    @Query("SELECT * FROM GameEntity")
    suspend fun getAllGames(): List<GameEntity>
}