package data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import data.local.dao.GameDao
import data.local.dao.PlayerDao
import data.local.dao.PlayerGameDao
import data.local.dao.RoundDao
import data.local.entities.GameEntity
import data.local.entities.PlayerEntity
import data.local.entities.PlayerGameEntity
import data.local.entities.RoundEntity
import data.local.entities.RoundOudlerEntity

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