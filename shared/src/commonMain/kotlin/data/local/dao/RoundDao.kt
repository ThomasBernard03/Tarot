package data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import commons.extensions.LocalDateTimeNow
import data.local.entities.RoundEntity
import data.local.entities.RoundOudlerEntity
import domain.models.Bid
import domain.models.Oudler
import domain.models.PlayerModel
import domain.models.RoundModel

@Dao
interface RoundDao {

    @Insert
    suspend fun insertRound(round : RoundEntity) : Long

    @Insert
    suspend fun insertOudler(oudler : RoundOudlerEntity) : Long

    @Query("SELECT * FROM RoundEntity WHERE gameId = :gameId")
    suspend fun getGameRounds(gameId : Long) : List<RoundEntity>


    @Query("SELECT * FROM RoundEntity WHERE id = :roundId")
    suspend fun getRound(roundId : Long) : RoundEntity

    @Query("SELECT oudler FROM RoundOudlerEntity WHERE roundId = :roundId")
    suspend fun getRoundOudlers(roundId : Long) : List<Oudler>

    @Query("DELETE FROM RoundEntity WHERE id = :roundId")
    suspend fun deleteRound(roundId : Long) : Int

    @Update
    suspend fun update(round : RoundEntity)

    @Transaction
    suspend fun createRound(
        gameId : Long,
        taker : PlayerModel,
        playerCalled : PlayerModel?,
        bid : Bid,
        oudlers : List<Oudler>,
        points : Int) : RoundModel {

        val round = RoundEntity(gameId = gameId, finishedAt = LocalDateTimeNow(), bid = bid, points = points, takerId = taker.id, calledPlayerId = playerCalled?.id)
        val roundId = insertRound(round)

        oudlers.forEach {
            val oudler = RoundOudlerEntity(roundId = roundId, oudler = it)
            insertOudler(oudler)
        }

        return RoundModel(
            id = roundId,
            finishedAt = round.finishedAt,
            taker = taker,
            bid = bid,
            oudlers = oudlers,
            points = points,
            calledPlayer = playerCalled
        )
    }
}