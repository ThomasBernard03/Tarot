package fr.thomasbernard03.tarot.domain.usecases

import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.player.GetPlayersError
import fr.thomasbernard03.tarot.domain.repositories.PlayerRepository
import org.koin.java.KoinJavaComponent.get

class GetPlayersUseCase(
    private val playerRepository: PlayerRepository = get(PlayerRepository::class.java)
) {
    suspend operator fun invoke() : Resource<List<PlayerModel>, GetPlayersError> {
        return playerRepository.getPlayers()
    }
}