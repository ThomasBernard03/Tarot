package fr.thomasbernard03.tarot.domain.usecases.player

import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.player.DeletePlayerError
import fr.thomasbernard03.tarot.domain.repositories.PlayerRepository
import org.koin.java.KoinJavaComponent.get

class DeletePlayerUseCase(
    private val playerRepository: PlayerRepository = get(PlayerRepository::class.java)
) {
    suspend operator fun invoke(id : Long): Resource<Unit, DeletePlayerError> {
        return playerRepository.deletePlayer(id)
    }
}