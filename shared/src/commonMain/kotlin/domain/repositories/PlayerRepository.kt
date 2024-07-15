package domain.repositories

import domain.models.CreatePlayerModel
import domain.models.PlayerColor
import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.player.CreatePlayerError
import domain.models.errors.player.DeletePlayerError
import domain.models.errors.player.EditPlayerError
import domain.models.errors.player.GetPlayerError
import domain.models.errors.player.GetPlayersError

interface PlayerRepository {
    suspend fun getPlayers() : Result<List<PlayerModel>>
    suspend fun createPlayer(player: CreatePlayerModel) : Resource<PlayerModel, CreatePlayerError>
    suspend fun deletePlayer(id: Long) : Resource<Unit, DeletePlayerError>
    suspend fun getPlayer(id: Long) : Resource<PlayerModel, GetPlayerError>
    suspend fun editPlayer(id : Long, name : String, color : PlayerColor) : Resource<PlayerModel, EditPlayerError>
}