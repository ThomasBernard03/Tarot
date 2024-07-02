package fr.thomasbernard03.tarot.domain.repositories

import fr.thomasbernard03.tarot.domain.models.Bid
import fr.thomasbernard03.tarot.domain.models.Oudler
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.RoundModel
import fr.thomasbernard03.tarot.domain.models.errors.CreateRoundError
import fr.thomasbernard03.tarot.domain.models.errors.DeleteRoundError
import fr.thomasbernard03.tarot.domain.models.errors.GetRoundError

interface RoundRepository {

    suspend fun createRound(
        gameId : Long,
        taker : PlayerModel,
        playerCalled : PlayerModel?,
        bid : Bid,
        oudlers : List<Oudler>,
        points : Int) : Resource<RoundModel, CreateRoundError>


    suspend fun deleteRound(roundId : Long) : Resource<Unit, DeleteRoundError>

    suspend fun getRound(gameId : Long, roundId : Long) : Resource<RoundModel, GetRoundError>
}