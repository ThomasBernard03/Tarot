package fr.thomasbernard03.tarot.domain.usecases.game

import domain.models.GameModel
import domain.models.Resource
import domain.models.errors.GetGameError
import domain.repositories.GameRepository
import org.koin.java.KoinJavaComponent.get

class GetGameUseCase(
    private val gameRepository: GameRepository = get(GameRepository::class.java)
) {
    suspend operator fun invoke(id : Long) : Resource<GameModel, GetGameError> {
        return gameRepository.getGame(id)
    }
}