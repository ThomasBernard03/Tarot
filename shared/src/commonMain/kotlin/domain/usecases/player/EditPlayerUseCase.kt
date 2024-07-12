package fr.thomasbernard03.tarot.domain.usecases.player

import domain.models.PlayerColor
import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.player.EditPlayerError
import domain.repositories.PlayerRepository
import org.koin.java.KoinJavaComponent.get

class EditPlayerUseCase(
    private val playerRepository: PlayerRepository = get(PlayerRepository::class.java)
) {
    suspend operator fun invoke(id : Long, name : String, color : PlayerColor): Resource<PlayerModel, EditPlayerError> {
        return playerRepository.editPlayer(id, name, color)
    }
}