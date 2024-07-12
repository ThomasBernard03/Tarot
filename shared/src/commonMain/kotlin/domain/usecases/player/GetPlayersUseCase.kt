package domain.usecases.player

import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.player.GetPlayersError
import domain.repositories.PlayerRepository

class GetPlayersUseCase(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke() : Resource<List<PlayerModel>, GetPlayersError> {
        return playerRepository.getPlayers()
    }
}