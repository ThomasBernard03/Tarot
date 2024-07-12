package data.local.dao

import androidx.room.Dao
import androidx.room.Query
import commons.extensions.LocalDateTimeNow
import data.local.entities.GameEntity
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Dao
interface GameDao {
    @Query("SELECT * FROM GameEntity WHERE finishedAt IS NULL")
    suspend fun getCurrentGame(): GameEntity?

    @Query("UPDATE GameEntity SET finishedAt = :finishedAt WHERE id = :id")
    suspend fun changeFinishedAt(id : Long, finishedAt : LocalDateTime? = LocalDateTimeNow())

    @Query("SELECT * FROM GameEntity WHERE id = :id")
    suspend fun getGame(id : Long): GameEntity

    @Query("DELETE FROM GameEntity WHERE id = :id")
    suspend fun deleteGame(id : Long)

    @Query("SELECT * FROM GameEntity")
    suspend fun getAllGames(): List<GameEntity>


    @Query("SELECT COUNT(1) >= 1  FROM GameEntity WHERE finishedAt IS NULL")
    suspend fun gameAlreadyInProgress() : Boolean
}