package domain.repositories

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
    suspend fun editRound(round : EditRoundModel) : Resource<Unit, EditRoundError>
}