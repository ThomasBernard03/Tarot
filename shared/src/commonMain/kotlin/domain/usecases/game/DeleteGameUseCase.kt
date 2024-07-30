package domain.usecases.game

import domain.models.Resource
import domain.models.errors.DeleteGameError
import domain.repositories.GameRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteGameUseCase : KoinComponent {
    private val gameRepository: GameRepository by inject()
    suspend operator fun invoke(id : Long) : Resource<Unit, DeleteGameError> {
        return gameRepository.deleteGame(id)
    }
}