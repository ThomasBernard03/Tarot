package domain.usecases.game

import domain.models.GameModel
import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.CreateGameError
import domain.repositories.GameRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CreateGameUseCase : KoinComponent {
    private val gameRepository: GameRepository by inject()

    suspend operator fun invoke(players : List<PlayerModel>) : Resource<GameModel, CreateGameError> {
        return gameRepository.createGame(players)
    }
}