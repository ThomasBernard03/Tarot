package fr.thomasbernard03.tarot.domain.usecases

import fr.thomasbernard03.tarot.domain.models.GameModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.GetGameError
import fr.thomasbernard03.tarot.domain.repositories.GameRepository
import org.koin.java.KoinJavaComponent.get

class GetGameUseCase(
    private val gameRepository: GameRepository = get(GameRepository::class.java)
) {
    suspend operator fun invoke(id : Long) : Resource<GameModel, GetGameError> {
        return gameRepository.getGame(id)
    }
}