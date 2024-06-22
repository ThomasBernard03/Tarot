package fr.thomasbernard03.tarot.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.thomasbernard03.tarot.data.local.dao.GameDao
import fr.thomasbernard03.tarot.data.local.dao.PlayerDao
import fr.thomasbernard03.tarot.data.local.dao.PlayerGameDao
import fr.thomasbernard03.tarot.data.local.dao.RoundDao
import fr.thomasbernard03.tarot.data.local.entities.GameEntity
import fr.thomasbernard03.tarot.data.local.entities.PlayerEntity
import fr.thomasbernard03.tarot.data.local.entities.PlayerGameEntity
import fr.thomasbernard03.tarot.data.local.entities.RoundEntity
import fr.thomasbernard03.tarot.data.local.entities.RoundOudlerEntity

@Database(
    entities = [
        PlayerEntity::class,
        GameEntity::class,
        PlayerGameEntity::class,
        RoundEntity::class,
        RoundOudlerEntity::class
    ],
    version = 3
)
@TypeConverters(DateConverter::class)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun gameDao(): GameDao
    abstract fun playerGameDao(): PlayerGameDao
    abstract fun roundDao(): RoundDao
}