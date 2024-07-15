package domain.usecases.game

import domain.models.Resource
import domain.models.errors.FinishGameError
import domain.repositories.GameRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FinishGameUseCase : KoinComponent {
    private val gameRepository: GameRepository by inject()
    suspend operator fun invoke(gameId: Long) : Resource<Unit, FinishGameError> {
        return gameRepository.finishGame(gameId)
    }
}