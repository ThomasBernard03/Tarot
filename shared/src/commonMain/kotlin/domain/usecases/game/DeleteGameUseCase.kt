package domain.usecases.game

import domain.models.Resource
import domain.models.errors.DeleteGameError
import domain.repositories.GameRepository

class DeleteGameUseCase(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(id : Long) : Resource<Unit, DeleteGameError> {
        return gameRepository.deleteGame(id)
    }
}