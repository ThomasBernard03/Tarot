package fr.thomasbernard03.tarot.domain.usecases.player

import domain.models.Resource
import domain.models.errors.player.DeletePlayerError
import domain.repositories.PlayerRepository
import org.koin.java.KoinJavaComponent.get

class DeletePlayerUseCase(
    private val playerRepository: PlayerRepository = get(PlayerRepository::class.java)
) {
    suspend operator fun invoke(id : Long): Resource<Unit, DeletePlayerError> {
        return playerRepository.deletePlayer(id)
    }
}