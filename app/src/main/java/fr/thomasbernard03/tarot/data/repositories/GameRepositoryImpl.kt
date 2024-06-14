package fr.thomasbernard03.tarot.data.repositories

import android.util.Log
import fr.thomasbernard03.tarot.data.local.dao.GameDao
import fr.thomasbernard03.tarot.data.local.dao.PlayerDao
import fr.thomasbernard03.tarot.data.local.dao.PlayerGameDao
import fr.thomasbernard03.tarot.data.local.entities.GameEntity
import fr.thomasbernard03.tarot.data.local.entities.PlayerEntity
import fr.thomasbernard03.tarot.data.local.entities.PlayerGameEntity
import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.Game
import fr.thomasbernard03.tarot.domain.models.Player
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.CreateGameError
import fr.thomasbernard03.tarot.domain.models.errors.GetGameError
import fr.thomasbernard03.tarot.domain.repositories.GameRepository
import org.koin.java.KoinJavaComponent.get
import java.lang.NullPointerException
import java.util.Date

class GameRepositoryImpl(
    private val playerDao: PlayerDao = get(PlayerDao::class.java),
    private val playerGameDao : PlayerGameDao = get(PlayerGameDao::class.java),
    private val gameDao: GameDao = get(GameDao::class.java)
) : GameRepository {


    override suspend fun createGame(players: List<CreatePlayerModel>): Resource<Game, CreateGameError> {
        return try {
            val gameAlreadyInProgress = gameDao.gameAlreadyInProgress()
            if (gameAlreadyInProgress)
                return Resource.Error(CreateGameError.GameAlreadyInProgress)

            if (players.size < 3 || players.size > 5)
                return Resource.Error(CreateGameError.InvalidNumberOfPlayers)

            val game = playerGameDao.createGameAndAddPlayer(players)

            Resource.Success(game)
        }
        catch (e: Exception) {
            Log.e(e.message, e.stackTraceToString())
            Resource.Error(CreateGameError.UnknownError)
        }
    }

    // TODO optimize this
    override suspend fun getAllGames(): Resource<List<Game>, GetGameError> {
        return try {
            val games = gameDao.getAllGames().map {
                val players = playerGameDao.getPlayersForGame(it.id!!).map { playerEntity ->
                    Player(id = playerEntity.id!!, name = playerEntity.name, color = playerEntity.color)
                }
                Game(id = it.id, startedAt = it.startedAt, players = players)
            }

            Resource.Success(games)
        } catch (e : Exception){
            Log.e(e.message, e.stackTraceToString())
            Resource.Error(GetGameError.UnknownError)
        }
    }

    override suspend fun getCurrentGame(): Resource<Game, GetGameError> {
        return try {
            val game = gameDao.getCurrentGame()

            return Resource.Success(
                Game(
                    id = game.id!!,
                    startedAt = game.startedAt,
                    players = playerGameDao.getPlayersForGame(game.id).map { playerEntity ->
                        Player(id = playerEntity.id!!, name = playerEntity.name, color = playerEntity.color)
                    }
                )
            )
        }
        catch (e : NullPointerException){
            Log.e(e.message, e.stackTraceToString())
            Resource.Error(GetGameError.GameNotFound)
        }
        catch (e : Exception){
            Log.e(e.message, e.stackTraceToString())
            Resource.Error(GetGameError.UnknownError)
        }
    }
}