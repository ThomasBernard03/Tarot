package fr.thomasbernard03.tarot.domain.usecases.game

import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.FinishGameError
import fr.thomasbernard03.tarot.domain.repositories.GameRepository
import org.koin.java.KoinJavaComponent.get

class FinishGameUseCase(
    private val gameRepository: GameRepository = get(GameRepository::class.java)
) {
    suspend operator fun invoke(gameId: Long) : Resource<Unit, FinishGameError> {
        return gameRepository.finishGame(gameId)
    }
}