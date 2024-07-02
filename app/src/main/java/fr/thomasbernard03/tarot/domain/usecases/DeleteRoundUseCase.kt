package fr.thomasbernard03.tarot.domain.usecases

import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.DeleteRoundError
import fr.thomasbernard03.tarot.domain.repositories.RoundRepository
import org.koin.java.KoinJavaComponent.get

class DeleteRoundUseCase(
    private val roundRepository: RoundRepository = get(RoundRepository::class.java)
) {
    suspend operator fun invoke(roundId : Long) : Resource<Unit, DeleteRoundError> {
        return roundRepository.deleteRound(roundId)
    }
}