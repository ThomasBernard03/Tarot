package domain.usecases.game

import domain.models.Resource
import domain.models.errors.ResumeGameError
import domain.repositories.GameRepository

class ResumeGameUseCase(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(id : Long) : Resource<Unit, ResumeGameError> {
        return gameRepository.resumeGame(id)
    }
}