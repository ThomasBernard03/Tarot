package fr.thomasbernard03.tarot.domain.usecases.player

import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.player.GetPlayerError
import domain.repositories.PlayerRepository
import org.koin.java.KoinJavaComponent.get

class GetPlayerUseCase(
    private val playerRepository: PlayerRepository = get(PlayerRepository::class.java)
) {
    suspend operator fun invoke(id : Long): Resource<PlayerModel, GetPlayerError> {
        return playerRepository.getPlayer(id)
    }
}