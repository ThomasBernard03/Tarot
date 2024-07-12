package fr.thomasbernard03.tarot.domain.usecases.player

import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.player.GetPlayersError
import domain.repositories.PlayerRepository
import org.koin.java.KoinJavaComponent.get

class GetPlayersUseCase(
    private val playerRepository: PlayerRepository = get(PlayerRepository::class.java)
) {
    suspend operator fun invoke() : Resource<List<PlayerModel>, GetPlayersError> {
        return playerRepository.getPlayers()
    }
}