package domain.usecases.player

import domain.models.CreatePlayerModel
import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.player.CreatePlayerError
import domain.repositories.PlayerRepository

class CreatePlayerUseCase(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(player : CreatePlayerModel): Resource<PlayerModel, CreatePlayerError> {
        return playerRepository.createPlayer(player)
    }
}