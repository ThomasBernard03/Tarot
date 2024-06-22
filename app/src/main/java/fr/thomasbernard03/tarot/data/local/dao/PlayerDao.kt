package fr.thomasbernard03.tarot.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.thomasbernard03.tarot.data.local.entities.PlayerEntity

@Dao
interface PlayerDao {
    @Query("SELECT * FROM PlayerEntity WHERE id = :id")
    suspend fun getPlayer(id : Long) : PlayerEntity
}