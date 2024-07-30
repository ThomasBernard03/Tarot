package domain.usecases.round

import domain.models.Resource
import domain.models.RoundModel
import domain.models.errors.GetRoundError
import domain.repositories.RoundRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetRoundUseCase: KoinComponent {
    private val roundRepository: RoundRepository by inject()
    suspend operator fun invoke(gameId : Long, roundId : Long) : Resource<RoundModel, GetRoundError> {
        return roundRepository.getRound(gameId, roundId)
    }
}