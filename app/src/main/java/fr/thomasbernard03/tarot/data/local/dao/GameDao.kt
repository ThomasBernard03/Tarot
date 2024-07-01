package fr.thomasbernard03.tarot.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import fr.thomasbernard03.tarot.data.local.entities.GameEntity
import java.util.Date

@Dao
interface GameDao {
    @Query("SELECT * FROM GameEntity WHERE finishedAt IS NULL")
    suspend fun getCurrentGame(): GameEntity

    @Query("UPDATE GameEntity SET finishedAt = :finishedAt WHERE id = :id")
    suspend fun gameFinished(id : Long, finishedAt : Date = Date())

    @Query("SELECT * FROM GameEntity WHERE id = :id")
    suspend fun getGame(id : Long): GameEntity

    @Query("SELECT * FROM GameEntity")
    suspend fun getAllGames(): List<GameEntity>


    @Query("SELECT COUNT(1) >= 1  FROM GameEntity WHERE finishedAt IS NULL")
    suspend fun gameAlreadyInProgress() : Boolean
}