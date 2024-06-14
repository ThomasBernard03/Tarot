package fr.thomasbernard03.tarot.domain.repositories

import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.Game
import fr.thomasbernard03.tarot.domain.models.Player
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.CreateGameError
import fr.thomasbernard03.tarot.domain.models.errors.GetGameError

interface GameRepository {
    suspend fun createGame(players : List<CreatePlayerModel>) : Resource<Game, CreateGameError>
    suspend fun getAllGames() : Resource<List<Game>, GetGameError>


    suspend fun getCurrentGame() : Resource<Game, GetGameError>
}