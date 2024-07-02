package fr.thomasbernard03.tarot.domain.usecases

import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.RoundModel
import fr.thomasbernard03.tarot.domain.models.errors.GetRoundError
import fr.thomasbernard03.tarot.domain.repositories.RoundRepository
import org.koin.java.KoinJavaComponent.get

class GetRoundUseCase(
    private val roundRepository: RoundRepository = get(RoundRepository::class.java)
) {
    suspend operator fun invoke(gameId : Long, roundId : Long) : Resource<RoundModel, GetRoundError> {
        return roundRepository.getRound(gameId, roundId)
    }
}