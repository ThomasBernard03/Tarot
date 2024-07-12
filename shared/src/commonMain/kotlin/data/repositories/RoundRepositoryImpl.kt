package data.repositories

import data.local.dao.PlayerDao
import data.local.dao.RoundDao
import data.local.entities.RoundEntity
import domain.models.Bid
import domain.models.EditRoundModel
import domain.models.Oudler
import domain.models.PlayerModel
import domain.models.Resource
import domain.models.RoundModel
import domain.models.errors.CreateRoundError
import domain.models.errors.DeleteRoundError
import domain.models.errors.EditRoundError
import domain.models.errors.GetRoundError
import domain.repositories.RoundRepository

class RoundRepositoryImpl(
    private val roundDao: RoundDao,
    private val playerDao: PlayerDao,
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
//            Log.e(e.message, e.stackTraceToString())
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
//            Log.e(e.message, e.stackTraceToString())
            Resource.Error(DeleteRoundError.UnknownError)
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
//            Log.e(e.message, e.stackTraceToString())
            Resource.Error(GetRoundError.UnknownError)
        }
    }

    override suspend fun editRound(round: EditRoundModel): Resource<Unit, EditRoundError> {
        return try {
            val existingRound = roundDao.getRound(round.id)

            val entity = RoundEntity(gameId = existingRound.gameId, id = round.id, takerId = round.taker.id, bid = round.bid, points = round.points, calledPlayerId = round.calledPlayer?.id, finishedAt = existingRound.finishedAt)
            roundDao.update(entity)

            return Resource.Success(Unit)
        }
        catch (e : NullPointerException){
            Resource.Error(EditRoundError.RoundNotFound(round.id))
        }
        catch (e : Exception){
//            Log.e(e.message, e.stackTraceToString())
            Resource.Error(EditRoundError.UnknownError)
        }
    }
}