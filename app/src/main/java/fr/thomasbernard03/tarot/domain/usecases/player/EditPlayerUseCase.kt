package fr.thomasbernard03.tarot.domain.usecases.player

import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.player.EditPlayerError
import fr.thomasbernard03.tarot.domain.repositories.PlayerRepository
import org.koin.java.KoinJavaComponent.get

class EditPlayerUseCase(
    private val playerRepository: PlayerRepository = get(PlayerRepository::class.java)
) {
    suspend operator fun invoke(id : Long, name : String, color : PlayerColor): Resource<PlayerModel, EditPlayerError> {
        return playerRepository.editPlayer(id, name, color)
    }
}