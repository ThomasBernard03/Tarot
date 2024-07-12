package fr.thomasbernard03.tarot.domain.usecases.game

import domain.models.Resource
import domain.models.errors.FinishGameError
import domain.repositories.GameRepository
import org.koin.java.KoinJavaComponent.get

class FinishGameUseCase(
    private val gameRepository: GameRepository = get(GameRepository::class.java)
) {
    suspend operator fun invoke(gameId: Long) : Resource<Unit, FinishGameError> {
        return gameRepository.finishGame(gameId)
    }
}