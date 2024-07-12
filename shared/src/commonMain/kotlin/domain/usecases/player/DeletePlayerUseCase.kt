package domain.usecases.player

import domain.models.Resource
import domain.models.errors.player.DeletePlayerError
import domain.repositories.PlayerRepository

class DeletePlayerUseCase(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(id : Long): Resource<Unit, DeletePlayerError> {
        return playerRepository.deletePlayer(id)
    }
}