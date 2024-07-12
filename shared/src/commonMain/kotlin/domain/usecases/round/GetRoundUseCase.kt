package domain.usecases.round

import domain.models.Resource
import domain.models.RoundModel
import domain.models.errors.GetRoundError
import domain.repositories.RoundRepository

class GetRoundUseCase(
    private val roundRepository: RoundRepository
) {
    suspend operator fun invoke(gameId : Long, roundId : Long) : Resource<RoundModel, GetRoundError> {
        return roundRepository.getRound(gameId, roundId)
    }
}