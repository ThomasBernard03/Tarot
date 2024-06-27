package fr.thomasbernard03.tarot.domain.usecases

import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.CreatePlayerError
import fr.thomasbernard03.tarot.domain.repositories.PlayerRepository
import org.koin.java.KoinJavaComponent.get

class CreatePlayerUseCase(
    private val playerRepository: PlayerRepository = get(PlayerRepository::class.java)
) {
    suspend operator fun invoke(player : CreatePlayerModel): Resource<PlayerModel, CreatePlayerError> {
        return playerRepository.createPlayer(player)
    }
}