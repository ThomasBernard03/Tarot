package fr.thomasbernard03.tarot.domain.usecases

import fr.thomasbernard03.tarot.domain.models.GameModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.GetGameError
import fr.thomasbernard03.tarot.domain.repositories.GameRepository
import org.koin.java.KoinJavaComponent.get

class GetGameHistoryUseCase(
    private val gameRepository: GameRepository = get(GameRepository::class.java)
) {
    suspend operator fun invoke() : Resource<List<GameModel>, GetGameError>  {
        return when(val result = gameRepository.getAllGames()){
            is Resource.Success -> {
                Resource.Success(result.data.sortedByDescending { it.finishedAt })
            }

            is Resource.Error -> {
                result
            }
        }
    }
}