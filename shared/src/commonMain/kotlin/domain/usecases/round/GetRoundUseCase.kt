package fr.thomasbernard03.tarot.domain.usecases.round

import domain.models.Resource
import domain.models.RoundModel
import domain.models.errors.GetRoundError
import domain.repositories.RoundRepository
import org.koin.java.KoinJavaComponent.get

class GetRoundUseCase(
    private val roundRepository: RoundRepository = get(RoundRepository::class.java)
) {
    suspend operator fun invoke(gameId : Long, roundId : Long) : Resource<RoundModel, GetRoundError> {
        return roundRepository.getRound(gameId, roundId)
    }
}