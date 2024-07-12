package fr.thomasbernard03.tarot.domain.repositories

import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.player.CreatePlayerError
import fr.thomasbernard03.tarot.domain.models.errors.player.DeletePlayerError
import fr.thomasbernard03.tarot.domain.models.errors.player.EditPlayerError
import fr.thomasbernard03.tarot.domain.models.errors.player.GetPlayerError
import fr.thomasbernard03.tarot.domain.models.errors.player.GetPlayersError

interface PlayerRepository {
    suspend fun getPlayers() : Resource<List<PlayerModel>, GetPlayersError>
    suspend fun createPlayer(player: CreatePlayerModel) : Resource<PlayerModel, CreatePlayerError>
    suspend fun deletePlayer(id: Long) : Resource<Unit, DeletePlayerError>
    suspend fun getPlayer(id: Long) : Resource<PlayerModel, GetPlayerError>
    suspend fun editPlayer(id : Long, name : String, color : PlayerColor) : Resource<PlayerModel, EditPlayerError>
}