package domain.usecases.game

import domain.models.GameModel
import domain.models.Resource
import domain.models.errors.GetGameError
import domain.repositories.GameRepository

class GetCurrentGameUseCase(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(): Resource<GameModel, GetGameError> {
        return gameRepository.getCurrentGame()
    }
}