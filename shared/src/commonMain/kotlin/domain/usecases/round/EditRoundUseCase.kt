package domain.usecases.round

import domain.models.EditRoundModel
import domain.models.Resource
import domain.models.errors.EditRoundError
import domain.repositories.RoundRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EditRoundUseCase: KoinComponent {
    private val roundRepository: RoundRepository by inject()
    suspend operator fun invoke(round : EditRoundModel) : Resource<Unit, EditRoundError> {
        return roundRepository.editRound(round)
    }
}