package fr.thomasbernard03.tarot.data.repositories

import android.util.Log
import fr.thomasbernard03.tarot.data.local.dao.RoundDao
import fr.thomasbernard03.tarot.domain.models.Bid
import fr.thomasbernard03.tarot.domain.models.Oudler
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.RoundModel
import fr.thomasbernard03.tarot.domain.models.errors.CreateRoundError
import fr.thomasbernard03.tarot.domain.repositories.RoundRepository
import org.koin.java.KoinJavaComponent.get

class RoundRepositoryImpl(
    private val roundDao: RoundDao = get(RoundDao::class.java)
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
}