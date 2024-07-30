package domain.usecases.player

import domain.models.Resource
import domain.models.errors.player.DeletePlayerError
import domain.repositories.PlayerRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeletePlayerUseCase : KoinComponent {
    private val playerRepository: PlayerRepository by inject()

    suspend operator fun invoke(id : Long): Resource<Unit, DeletePlayerError> {
        return playerRepository.deletePlayer(id)
    }
}