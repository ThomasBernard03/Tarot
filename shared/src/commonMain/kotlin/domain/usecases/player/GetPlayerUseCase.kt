package domain.usecases.player

import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.player.GetPlayerError
import domain.repositories.PlayerRepository

class GetPlayerUseCase(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(id : Long): Resource<PlayerModel, GetPlayerError> {
        return playerRepository.getPlayer(id)
    }
}