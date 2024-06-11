package fr.thomasbernard03.tarot.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.thomasbernard03.tarot.data.local.entities.PlayerEntity
import fr.thomasbernard03.tarot.data.local.entities.PlayerGameEntity

@Dao
interface PlayerGameDao {
    @Insert
    suspend fun addPlayerToGame(playersGameEntity: List<PlayerGameEntity>) : List<Long>

    @Query("""
        SELECT PlayerEntity.*
        FROM PlayerEntity
        INNER JOIN PlayerGameEntity ON PlayerEntity.id = PlayerGameEntity.playerId
        WHERE PlayerGameEntity.gameId = :gameId
    """)
    suspend fun getPlayersForGame(gameId: Long) : List<PlayerEntity>
}