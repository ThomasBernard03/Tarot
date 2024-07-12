package fr.thomasbernard03.tarot.domain.usecases.game

import fr.thomasbernard03.tarot.domain.models.GameModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.GetGameError
import fr.thomasbernard03.tarot.domain.repositories.GameRepository
import org.koin.java.KoinJavaComponent.get

class GetCurrentGameUseCase(
    private val gameRepository: GameRepository = get(GameRepository::class.java)
) {
    suspend operator fun invoke(): Resource<GameModel, GetGameError> {
        return gameRepository.getCurrentGame()
    }
}