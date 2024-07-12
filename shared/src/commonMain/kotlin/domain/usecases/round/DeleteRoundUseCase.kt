package fr.thomasbernard03.tarot.domain.usecases.round

import domain.models.Resource
import domain.models.errors.DeleteRoundError
import domain.repositories.RoundRepository
import org.koin.java.KoinJavaComponent.get

class DeleteRoundUseCase(
    private val roundRepository: RoundRepository = get(RoundRepository::class.java)
) {
    suspend operator fun invoke(roundId : Long) : Resource<Unit, DeleteRoundError> {
        return roundRepository.deleteRound(roundId)
    }
}