package domain.usecases.game

import domain.models.GameModel
import domain.models.Resource
import domain.models.errors.GetGameError
import domain.repositories.GameRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetGameUseCase : KoinComponent {
    private val gameRepository: GameRepository by inject()

    suspend operator fun invoke(id : Long) : Resource<GameModel, GetGameError> {
        return gameRepository.getGame(id)
    }
}