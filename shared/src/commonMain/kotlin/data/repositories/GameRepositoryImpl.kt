package data.repositories

import commons.Logger
import data.local.ApplicationDatabase
import data.local.dao.GameDao
import data.local.dao.PlayerDao
import data.local.dao.PlayerGameDao
import data.local.dao.RoundDao
import domain.models.GameModel
import domain.models.PlayerModel
import domain.models.Resource
import domain.models.RoundModel
import domain.models.errors.CreateGameError
import domain.models.errors.DeleteGameError
import domain.models.errors.FinishGameError
import domain.models.errors.GetGameError
import domain.models.errors.ResumeGameError
import domain.repositories.GameRepository

class GameRepositoryImpl(
    database : ApplicationDatabase,
    private val logger : Logger
) : GameRepository {

    private val playerDao: PlayerDao = database.playerDao()
    private val playerGameDao : PlayerGameDao = database.playerGameDao()
    private val gameDao: GameDao = database.gameDao()
    private val roundDao: RoundDao = database.roundDao()

    override suspend fun deleteGame(id: Long): Resource<Unit, DeleteGameError> {
        return try {
            val entity =  gameDao.getGame(id)
            gameDao.deleteGame(entity.id!!)
            return Resource.Success(Unit)
        }
        catch (e : NullPointerException){
            Resource.Error(DeleteGameError.GameNotFound)
        }
        catch (e : Exception){
            logger.e(e)
            Resource.Error(DeleteGameError.UnknownError)
        }
    }


    override suspend fun createGame(players: List<PlayerModel>): Resource<GameModel, CreateGameError> {
        return try {
            val gameAlreadyInProgress = gameDao.gameAlreadyInProgress()
            if (gameAlreadyInProgress)
                return Resource.Error(CreateGameError.GameAlreadyInProgress)

            if (players.size < 3 || players.size > 5)
                return Resource.Error(CreateGameError.InvalidNumberOfPlayers)

            val game = playerGameDao.createGameAndLinkPlayers(players)

            Resource.Success(game)
        }
        catch (e: Exception) {
            logger.e(e)
            Resource.Error(CreateGameError.UnknownError)
        }
    }

    override suspend fun finishGame(id: Long): Resource<Unit, FinishGameError> {
        return try {
            val game = gameDao.getGame(id)

            if (game.finishedAt != null)
                return Resource.Error(FinishGameError.GameAlreadyFinished)

            gameDao.changeFinishedAt(id)

            Resource.Success(Unit)
        }
        catch (e : NullPointerException){
            logger.e(e)
            Resource.Error(FinishGameError.GameNotFound)
        }
        catch (e : Exception){
            logger.e(e)
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

                val rounds = roundDao.getGameRounds(it.id!!)


                GameModel(
                    id = it.id,
                    startedAt = it.startedAt,
                    players = players,
                    rounds = rounds.map {
                        val takerEntity = playerDao.getPlayer(it.takerId)
                        val calledPlayer = it.calledPlayerId?.let { playerDao.getPlayer(it) }
                        val oudlers = roundDao.getRoundOudlers(it.id!!)

                        RoundModel(
                            id = it.id,
                            taker = PlayerModel(id = takerEntity.id!!, name = takerEntity.name, color = takerEntity.color),
                            bid = it.bid,
                            oudlers = oudlers,
                            points = it.points,
                            calledPlayer = calledPlayer?.let { PlayerModel(id = it.id!!, name = it.name, color = it.color) },
                            finishedAt = it.finishedAt
                        )

                    },
                    finishedAt = it.finishedAt
                )
            }

            Resource.Success(games)
        } catch (e : Exception){
            logger.e(e)
            Resource.Error(GetGameError.UnknownError)
        }
    }

    override suspend fun getCurrentGame(): Resource<GameModel, GetGameError> {
        return try {
            val game = gameDao.getCurrentGame()

            if (game == null)
                return Resource.Error(GetGameError.GameNotFound)

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
        catch (e : Exception){
            logger.e(e)
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
            logger.e(e)
            Resource.Error(GetGameError.UnknownError)
        }
    }

    override suspend fun resumeGame(id: Long): Resource<Unit, ResumeGameError> {
        return try {
            gameDao.getCurrentGame()?.let {
                gameDao.changeFinishedAt(it.id!!)
            }

            gameDao.changeFinishedAt(id, null)
            return Resource.Success(Unit)
        }
        catch (e : Exception){
            logger.e(e)
            Resource.Error(ResumeGameError.UnknownError)
        }
    }
}