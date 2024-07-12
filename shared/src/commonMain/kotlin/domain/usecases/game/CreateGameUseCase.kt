package domain.usecases.game

import domain.models.GameModel
import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.CreateGameError
import domain.repositories.GameRepository
import org.koin.java.KoinJavaComponent.get

class CreateGameUseCase(
    private val gameRepository: GameRepository = get(GameRepository::class.java)
) {
    suspend operator fun invoke(players : List<PlayerModel>) : Resource<GameModel, CreateGameError> {
        return gameRepository.createGame(players)
    }
}