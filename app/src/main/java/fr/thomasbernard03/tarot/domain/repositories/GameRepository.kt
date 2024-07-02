package fr.thomasbernard03.tarot.domain.repositories

import fr.thomasbernard03.tarot.domain.models.GameModel
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.CreateGameError
import fr.thomasbernard03.tarot.domain.models.errors.FinishGameError
import fr.thomasbernard03.tarot.domain.models.errors.GetGameError
import fr.thomasbernard03.tarot.domain.models.errors.ResumeGameError

interface GameRepository {
    suspend fun createGame(players : List<PlayerModel>) : Resource<GameModel, CreateGameError>
    suspend fun finishGame(id : Long) : Resource<Unit, FinishGameError>

    suspend fun getAllGames() : Resource<List<GameModel>, GetGameError>


    suspend fun getCurrentGame() : Resource<GameModel, GetGameError>
    suspend fun getGame(id : Long) : Resource<GameModel, GetGameError>


    suspend fun resumeGame(id : Long) : Resource<Unit, ResumeGameError>
}