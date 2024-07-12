package fr.thomasbernard03.tarot.domain.usecases.player

import domain.models.CreatePlayerModel
import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.player.CreatePlayerError
import domain.repositories.PlayerRepository
import org.koin.java.KoinJavaComponent.get

class CreatePlayerUseCase(
    private val playerRepository: PlayerRepository = get(PlayerRepository::class.java)
) {
    suspend operator fun invoke(player : CreatePlayerModel): Resource<PlayerModel, CreatePlayerError> {
        return playerRepository.createPlayer(player)
    }
}