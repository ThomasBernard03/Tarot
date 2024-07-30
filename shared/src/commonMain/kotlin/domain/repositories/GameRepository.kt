package domain.repositories

import domain.models.GameModel
import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.CreateGameError
import domain.models.errors.DeleteGameError
import domain.models.errors.FinishGameError
import domain.models.errors.GetGameError
import domain.models.errors.ResumeGameError

interface GameRepository {
    suspend fun createGame(players : List<PlayerModel>) : Resource<GameModel, CreateGameError>
    suspend fun finishGame(id : Long) : Resource<Unit, FinishGameError>

    suspend fun getAllGames() : Resource<List<GameModel>, GetGameError>

    suspend fun deleteGame(id : Long) : Resource<Unit, DeleteGameError>


    suspend fun getCurrentGame() : Resource<GameModel, GetGameError>
    suspend fun getGame(id : Long) : Resource<GameModel, GetGameError>


    suspend fun resumeGame(id : Long) : Resource<Unit, ResumeGameError>
}