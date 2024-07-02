package fr.thomasbernard03.tarot.data.repositories

import android.util.Log
import fr.thomasbernard03.tarot.data.local.dao.PlayerDao
import fr.thomasbernard03.tarot.data.local.dao.PlayerGameDao
import fr.thomasbernard03.tarot.data.local.dao.RoundDao
import fr.thomasbernard03.tarot.domain.models.Bid
import fr.thomasbernard03.tarot.domain.models.Oudler
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.RoundModel
import fr.thomasbernard03.tarot.domain.models.errors.CreateRoundError
import fr.thomasbernard03.tarot.domain.models.errors.DeleteRoundError
import fr.thomasbernard03.tarot.domain.models.errors.GetRoundError
import fr.thomasbernard03.tarot.domain.repositories.RoundRepository
import org.koin.java.KoinJavaComponent.get

class RoundRepositoryImpl(
    private val roundDao: RoundDao = get(RoundDao::class.java),
    private val playerDao: PlayerDao = get(PlayerDao::class.java),
) : RoundRepository {
    override suspend fun createRound(
        gameId: Long,
        taker: PlayerModel,
        playerCalled: PlayerModel?,
        bid: Bid,
        oudlers: List<Oudler>,
        points: Int
    ): Resource<RoundModel, CreateRoundError> {
        return try {
            val round = roundDao.createRound(gameId, taker, playerCalled, bid, oudlers, points)
            return Resource.Success(round)
        }
        catch (e : Exception){
            Log.e(e.message, e.stackTraceToString())
            Resource.Error(CreateRoundError.UnkownError)
        }
    }

    override suspend fun deleteRound(roundId: Long): Resource<Unit, DeleteRoundError> {
        return try {
            val modifiedLines = roundDao.deleteRound(roundId)

            if (modifiedLines == 0){
                return Resource.Error(DeleteRoundError.RoundNotFound(roundId))
            }

            return Resource.Success(Unit)
        }
        catch (e : Exception){
            Log.e(e.message, e.stackTraceToString())
            Resource.Error(DeleteRoundError.UnkownError)
        }
    }

    override suspend fun getRound(gameId: Long, roundId: Long): Resource<RoundModel, GetRoundError> {
        return try {
            val roundEntity = roundDao.getRound(roundId)
            val takerEntity = playerDao.getPlayer(roundEntity.takerId)
            val taker = PlayerModel(id = takerEntity.id!!, name = takerEntity.name, color = takerEntity.color)
            val playerCalled = roundEntity.calledPlayerId?.let {
                val entity = playerDao.getPlayer(it)
                PlayerModel(id = entity.id!!, name = entity.name, color = entity.color)
            }


            val round = RoundModel(
                id = roundEntity.id!!,
                finishedAt = roundEntity.finishedAt,
                taker = taker,
                bid = roundEntity.bid,
                oudlers = roundDao.getRoundOudlers(roundId),
                points = roundEntity.points,
                calledPlayer = playerCalled
            )
            return Resource.Success(round)
        }
        catch (e : NullPointerException){
            return Resource.Error(GetRoundError.RoundNotFound(roundId))
        }
        catch (e : Exception){
            Log.e(e.message, e.stackTraceToString())
            Resource.Error(GetRoundError.UnknownError)
        }
    }
}