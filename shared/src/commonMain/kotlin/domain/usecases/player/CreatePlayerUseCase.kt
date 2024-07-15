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

    @Throws(CreatePlayerError::class, CancellationException::class)
    suspend operator fun invoke(player : CreatePlayerModel): PlayerModel {
        val result =  playerRepository.createPlayer(player)
        println("creating player")

        when(result){
            is Resource.Error -> {
                throw result.data
            }
            is Resource.Success -> return result.data
        }
    }
}