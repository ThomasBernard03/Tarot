package domain.usecases.round

import domain.models.Resource
import domain.models.errors.DeleteRoundError
import domain.repositories.RoundRepository

class DeleteRoundUseCase(
    private val roundRepository: RoundRepository
) {
    suspend operator fun invoke(roundId : Long) : Resource<Unit, DeleteRoundError> {
        return roundRepository.deleteRound(roundId)
    }
}