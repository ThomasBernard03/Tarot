package fr.thomasbernard03.tarot.domain.repositories

import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.Game
import fr.thomasbernard03.tarot.domain.models.Player
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.CreateGameError

interface GameRepository {
    suspend fun createGame(players : List<CreatePlayerModel>) : Resource<Game, CreateGameError>
}