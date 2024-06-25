package fr.thomasbernard03.tarot.data.repositories

import android.util.Log
import fr.thomasbernard03.tarot.data.local.dao.GameDao
import fr.thomasbernard03.tarot.data.local.dao.PlayerDao
import fr.thomasbernard03.tarot.data.local.dao.PlayerGameDao
import fr.thomasbernard03.tarot.data.local.dao.RoundDao
import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.GameModel
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.RoundModel
import fr.thomasbernard03.tarot.domain.models.errors.CreateGameError
import fr.thomasbernard03.tarot.domain.models.errors.FinishGameError
import fr.thomasbernard03.tarot.domain.models.errors.GetGameError
import fr.thomasbernard03.tarot.domain.repositories.GameRepository
import org.koin.java.KoinJavaComponent.get
import kotlin.NullPointerException

class GameRepositoryImpl(
    private val playerDao: PlayerDao = get(PlayerDao::class.java),
    private val playerGameDao : PlayerGameDao = get(PlayerGameDao::class.java),
    private val gameDao: GameDao = get(GameDao::class.java),
    private val roundDao: RoundDao = get(RoundDao::class.java)
) : GameRepository {


    override suspend fun createGame(players: List<CreatePlayerModel>): Resource<GameModel, CreateGameError> {
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

    override suspend fun finishGame(id: Long): Resource<Unit, FinishGameError> {
        return try {
            val game = gameDao.getGame(id)

            if (game.finishedAt != null)
                return Resource.Error(FinishGameError.GameAlreadyFinished)

            gameDao.gameFinished(id)

            Resource.Success(Unit)
        }
        catch (e : NullPointerException){
            Log.e(e.message, e.stackTraceToString())
            Resource.Error(FinishGameError.GameNotFound)
        }
        catch (e : Exception){
            Log.e(e.message, e.stackTraceToString())
            Resource.Error(FinishGameError.UnknownError)
        }
    }

    // TODO optimize this
    override suspend fun getAllGames(): Resource<List<GameModel>, GetGameError> {
        return try {
            val games = gameDao.getAllGames().map {
                val players = playerGameDao.getPlayersForGame(it.id!!).map { playerEntity ->
                    PlayerModel(id = playerEntity.id!!, name = playerEntity.name, color = playerEntity.color)
                }
                GameModel(
                    id = it.id,
                    startedAt = it.startedAt,
                    players = players,
                    rounds = listOf()
                )
            }

            Resource.Success(games)
        } catch (e : Exception){
            Log.e(e.message, e.stackTraceToString())
            Resource.Error(GetGameError.UnknownError)
        }
    }

    override suspend fun getCurrentGame(): Resource<GameModel, GetGameError> {
        return try {
            val game = gameDao.getCurrentGame()
            val rounds = roundDao.getGameRounds(game.id!!)

            return Resource.Success(
                GameModel(
                    id = game.id,
                    startedAt = game.startedAt,
                    players = playerGameDao.getPlayersForGame(game.id).map { playerEntity ->
                        PlayerModel(id = playerEntity.id!!, name = playerEntity.name, color = playerEntity.color)
                    },
                    rounds = rounds.map {

                        val takerEntity = playerDao.getPlayer(it.takerId)
                        val calledPlayer = it.calledPlayerId?.let { playerDao.getPlayer(it) }
                        val oudlers = roundDao.getRoundOudlers(it.id!!)

                        RoundModel(
                            id = it.id,
                            finishedAt = it.finishedAt,
                            taker = PlayerModel(id = takerEntity.id!!, name = takerEntity.name, color = takerEntity.color),
                            bid = it.bid,
                            oudlers = oudlers,
                            points = it.points,
                            calledPlayer = calledPlayer?.let { PlayerModel(id = it.id!!, name = it.name, color = it.color) }
                        )
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

    override suspend fun getGame(id: Long): Resource<GameModel, GetGameError> {
        return try {
            val game = gameDao.getGame(id)
            val rounds = roundDao.getGameRounds(id)

            return Resource.Success(
                GameModel(
                    id = game.id!!,
                    startedAt = game.startedAt,
                    players = playerGameDao.getPlayersForGame(game.id).map { playerEntity ->
                        PlayerModel(id = playerEntity.id!!, name = playerEntity.name, color = playerEntity.color)
                    },
                    rounds = rounds.map {

                        val takerEntity = playerDao.getPlayer(it.takerId)
                        val calledPlayer = it.calledPlayerId?.let { playerDao.getPlayer(it) }
                        val oudlers = roundDao.getRoundOudlers(it.id!!)

                        RoundModel(
                            id = it.id,
                            finishedAt = it.finishedAt,
                            taker = PlayerModel(id = takerEntity.id!!, name = takerEntity.name, color = takerEntity.color),
                            bid = it.bid,
                            oudlers = oudlers,
                            points = it.points,
                            calledPlayer = calledPlayer?.let { PlayerModel(id = it.id!!, name = it.name, color = it.color) }
                        )
                    }
                )
            )
        }
        catch (e : Exception){
            Log.e(e.message, e.stackTraceToString())
            Resource.Error(GetGameError.UnknownError)
        }
    }
}