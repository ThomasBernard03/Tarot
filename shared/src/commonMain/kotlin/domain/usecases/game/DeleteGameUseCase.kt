package fr.thomasbernard03.tarot.domain.usecases.game

import domain.models.Resource
import domain.models.errors.DeleteGameError
import domain.repositories.GameRepository
import org.koin.java.KoinJavaComponent.get

class DeleteGameUseCase(
    private val gameRepository: GameRepository = get(GameRepository::class.java)
) {
    suspend operator fun invoke(id : Long) : Resource<Unit, DeleteGameError> {
        return gameRepository.deleteGame(id)
    }
}