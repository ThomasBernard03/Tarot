package fr.thomasbernard03.tarot.domain.usecases.game

import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.ResumeGameError
import fr.thomasbernard03.tarot.domain.repositories.GameRepository
import org.koin.java.KoinJavaComponent.get

class ResumeGameUseCase(
    private val gameRepository: GameRepository = get(GameRepository::class.java)
) {
    suspend operator fun invoke(id : Long) : Resource<Unit, ResumeGameError> {
        return gameRepository.resumeGame(id)
    }
}