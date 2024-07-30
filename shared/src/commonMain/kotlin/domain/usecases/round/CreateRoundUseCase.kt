package domain.usecases.round

import domain.models.Bid
import domain.models.Oudler
import domain.models.PlayerModel
import domain.models.Resource
import domain.models.RoundModel
import domain.models.errors.CreateRoundError
import domain.repositories.RoundRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CreateRoundUseCase : KoinComponent {
    private val roundRepository: RoundRepository by inject()
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