package domain.usecases.player

import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.player.GetPlayersError
import domain.repositories.PlayerRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetPlayersUseCase : KoinComponent {
    private val playerRepository: PlayerRepository by inject()

    suspend operator fun invoke() : Resource<List<PlayerModel>, GetPlayersError> {
        return playerRepository.getPlayers()
    }
}