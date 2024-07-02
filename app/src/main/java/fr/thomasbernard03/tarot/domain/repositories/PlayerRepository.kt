package fr.thomasbernard03.tarot.domain.repositories

import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.CreatePlayerError
import fr.thomasbernard03.tarot.domain.models.errors.GetPlayersError

interface PlayerRepository {
    suspend fun getPlayers() : Resource<List<PlayerModel>, GetPlayersError>

    suspend fun createPlayer(player: CreatePlayerModel) : Resource<PlayerModel, CreatePlayerError>
}