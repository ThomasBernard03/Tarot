package fr.thomasbernard03.tarot.domain.usecases

import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.DeleteGameError
import fr.thomasbernard03.tarot.domain.repositories.GameRepository
import org.koin.java.KoinJavaComponent.get

class DeleteGameUseCase(
    private val gameRepository: GameRepository = get(GameRepository::class.java)
) {
    suspend operator fun invoke(id : Long) : Resource<Unit, DeleteGameError> {
        return gameRepository.deleteGame(id)
    }
}