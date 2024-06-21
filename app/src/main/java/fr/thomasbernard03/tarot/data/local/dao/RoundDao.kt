package fr.thomasbernard03.tarot.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import fr.thomasbernard03.tarot.data.local.entities.RoundEntity
import fr.thomasbernard03.tarot.data.local.entities.TakerEntity
import fr.thomasbernard03.tarot.data.local.entities.TakerOudlerEntity
import fr.thomasbernard03.tarot.domain.models.Bid
import fr.thomasbernard03.tarot.domain.models.Oudler
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.RoundModel
import fr.thomasbernard03.tarot.domain.models.TakerModel
import java.util.Date

@Dao
interface RoundDao {

    @Insert
    suspend fun insertRound(round : RoundEntity) : Long

    @Insert
    suspend fun insertTaker(taker : TakerEntity) : Long


    @Insert
    suspend fun insertOudler(oudler : TakerOudlerEntity) : Long

    @Transaction
    suspend fun createRound(
        gameId : Long,
        taker : PlayerModel,
        playerCalled : PlayerModel?,
        bid : Bid,
        oudlers : List<Oudler>,
        points : Int) : RoundModel {

        val round = RoundEntity(gameId = gameId, finishedAt = Date())
        val roundId = insertRound(round)

        val takerEntity = TakerEntity(roundId = roundId, playerId = taker.id, bid = bid, points = points)
        val takerId = insertTaker(takerEntity)

        oudlers.forEach {
            val oudler = TakerOudlerEntity(takerId = takerId, oudler = it)
            insertOudler(oudler)
        }

        return RoundModel(
            id = roundId,
            finishedAt = round.finishedAt,
            taker = TakerModel(
                player = taker,
                bid = bid,
                oudlers = oudlers,
                points = points
            ),
        )
    }
}