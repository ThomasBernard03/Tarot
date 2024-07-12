package domain.usecases.game

import domain.models.GameModel
import domain.models.Resource
import domain.models.errors.GetGameError
import domain.repositories.GameRepository

class GetGameUseCase(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(id : Long) : Resource<GameModel, GetGameError> {
        return gameRepository.getGame(id)
    }
}