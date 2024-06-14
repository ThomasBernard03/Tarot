package fr.thomasbernard03.tarot.domain.repositories

import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.GameModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.CreateGameError
import fr.thomasbernard03.tarot.domain.models.errors.GetGameError

interface GameRepository {
    suspend fun createGame(players : List<CreatePlayerModel>) : Resource<GameModel, CreateGameError>
    suspend fun getAllGames() : Resource<List<GameModel>, GetGameError>


    suspend fun getCurrentGame() : Resource<GameModel, GetGameError>
}