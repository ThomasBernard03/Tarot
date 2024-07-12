package fr.thomasbernard03.tarot.domain.usecases.round

import fr.thomasbernard03.tarot.domain.models.EditRoundModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.EditRoundError
import fr.thomasbernard03.tarot.domain.repositories.RoundRepository
import org.koin.java.KoinJavaComponent.get

class EditRoundUseCase(
    private val roundRepository: RoundRepository = get(RoundRepository::class.java)
) {
    suspend operator fun invoke(round : EditRoundModel) : Resource<Unit, EditRoundError> {
        return roundRepository.editRound(round)
    }
}