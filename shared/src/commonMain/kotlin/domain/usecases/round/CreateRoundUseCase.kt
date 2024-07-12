package domain.usecases.round

import domain.models.Bid
import domain.models.Oudler
import domain.models.PlayerModel
import domain.models.Resource
import domain.models.RoundModel
import domain.models.errors.CreateRoundError
import domain.repositories.RoundRepository

class CreateRoundUseCase(
    private val roundRepository: RoundRepository
) {
    suspend operator fun invoke(
        gameId : Long,
        taker : PlayerModel,
        playerCalled : PlayerModel?,
        bid : Bid,
        oudlers : List<Oudler>,
        points : Int
    ) : Resource<RoundModel, CreateRoundError> {
        return roundRepository.createRound(gameId, taker, playerCalled, bid, oudlers, points)
    }
}