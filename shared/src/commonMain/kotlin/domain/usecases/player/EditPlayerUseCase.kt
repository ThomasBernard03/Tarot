package domain.usecases.player

import domain.models.PlayerColor
import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.player.EditPlayerError
import domain.repositories.PlayerRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EditPlayerUseCase : KoinComponent {
    private val playerRepository: PlayerRepository by inject()

    suspend operator fun invoke(id : Long, name : String, color : PlayerColor): Resource<PlayerModel, EditPlayerError> {
        return playerRepository.editPlayer(id, name, color)
    }
}