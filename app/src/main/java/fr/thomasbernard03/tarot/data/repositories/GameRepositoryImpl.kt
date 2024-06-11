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
import java.util.Date

class GameRepositoryImpl(
    private val playerDao: PlayerDao = get(PlayerDao::class.java),
    private val playerGameDao : PlayerGameDao = get(PlayerGameDao::class.java),
    private val gameDao: GameDao = get(GameDao::class.java)
) : GameRepository {


    // Use transactions to ensure that all operations are successful
    override suspend fun createGame(players: List<CreatePlayerModel>): Resource<Game, CreateGameError> {
        try {
            // TODO Check if game is not already in progress

            // 1) Insert all players
            val playersEntities = players.map {
                PlayerEntity(name = it.name, color = it.color)
            }
            playerDao.insertPlayers(playersEntities)

            // 2) Create a game
            val gameEntity = GameEntity(startedAt = Date())
            val gameId = gameDao.createGame(gameEntity)

            // 3) Insert all players in the game
            val playerGameEntities = playersEntities.map { PlayerGameEntity(playerId = it.id!!, gameId = gameId) }
            playerGameDao.addPlayerToGame(playerGameEntities)

            val resultPlayers = playerGameDao.getPlayersForGame(gameId).map {
                Player(id = it.id!!, name = it.name, color = it.color)
            }

            return Resource.Success(Game(id = gameId, startedAt = gameEntity.startedAt, players = resultPlayers))
        }
        catch (e: Exception) {
            Log.e(e.message, e.stackTraceToString())
            return Resource.Error(CreateGameError.UnknownError)
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
}