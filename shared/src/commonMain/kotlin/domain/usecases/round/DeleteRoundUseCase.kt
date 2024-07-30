package domain.usecases.round

import domain.models.Resource
import domain.models.errors.DeleteRoundError
import domain.repositories.RoundRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteRoundUseCase: KoinComponent {
    private val roundRepository: RoundRepository by inject()
    suspend operator fun invoke(roundId : Long) : Resource<Unit, DeleteRoundError> {
        return roundRepository.deleteRound(roundId)
    }
}