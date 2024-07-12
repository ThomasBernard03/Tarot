package domain.usecases.round

import domain.models.EditRoundModel
import domain.models.Resource
import domain.models.errors.EditRoundError
import domain.repositories.RoundRepository

class EditRoundUseCase(
    private val roundRepository: RoundRepository
) {
    suspend operator fun invoke(round : EditRoundModel) : Resource<Unit, EditRoundError> {
        return roundRepository.editRound(round)
    }
}