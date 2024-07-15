package domain.usecases.player

import domain.models.CreatePlayerModel
import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.player.CreatePlayerError
import domain.models.errors.player.GetPlayersError
import domain.repositories.PlayerRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

class CreatePlayerUseCase : KoinComponent {
    private val playerRepository: PlayerRepository by inject()

    suspend operator fun invoke(player : CreatePlayerModel): Resource<PlayerModel, CreatePlayerError> {
        return playerRepository.createPlayer(player)
    }
}