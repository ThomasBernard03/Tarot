package domain.usecases.game

import domain.models.Resource
import domain.models.errors.FinishGameError
import domain.repositories.GameRepository

class FinishGameUseCase(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(gameId: Long) : Resource<Unit, FinishGameError> {
        return gameRepository.finishGame(gameId)
    }
}