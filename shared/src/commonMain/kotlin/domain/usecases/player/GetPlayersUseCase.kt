package domain.usecases.player

import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.player.GetPlayersError
import domain.repositories.PlayerRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

class GetPlayersUseCase : KoinComponent {
    private val playerRepository: PlayerRepository by inject()

    @Throws(GetPlayersError.UnknownError::class, CancellationException::class)
    suspend fun execute() : List<PlayerModel> {
        val result = playerRepository.getPlayers()

        if (result.isFailure)
            throw result.exceptionOrNull()!!

        return result.getOrNull()!!
    }
}