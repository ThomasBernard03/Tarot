package domain.usecases.game

import domain.models.GameModel
import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.CreateGameError
import domain.repositories.GameRepository

class CreateGameUseCase(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(players : List<PlayerModel>) : Resource<GameModel, CreateGameError> {
        return gameRepository.createGame(players)
    }
}