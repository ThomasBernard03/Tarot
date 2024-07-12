package domain.usecases.game

import domain.models.GameModel
import domain.models.Resource
import domain.models.errors.GetGameError
import domain.repositories.GameRepository

class GetGameHistoryUseCase(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke() : Resource<List<GameModel>, GetGameError> {
        return when(val result = gameRepository.getAllGames()){
            is Resource.Success -> {
                val sortedGames = result.data.filter { it.finishedAt != null }.sortedByDescending { it.finishedAt }
                Resource.Success(sortedGames)
            }
            is Resource.Error -> {
                result
            }
        }
    }
}