package fr.thomasbernard03.tarot.domain.usecases

import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.Game
import fr.thomasbernard03.tarot.domain.models.Player
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.CreateGameError
import fr.thomasbernard03.tarot.domain.repositories.GameRepository
import org.koin.java.KoinJavaComponent.get

class CreateGameUseCase(
    private val gameRepository: GameRepository = get(GameRepository::class.java)
) {
    suspend operator fun invoke(players : List<CreatePlayerModel>) : Resource<Game, CreateGameError> {
        return gameRepository.createGame(players)
    }
}