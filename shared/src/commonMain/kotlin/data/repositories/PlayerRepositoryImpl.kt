package data.repositories

import androidx.sqlite.SQLiteException
import commons.Logger
import data.local.ApplicationDatabase
import data.local.dao.PlayerDao
import data.local.entities.PlayerEntity
import domain.models.CreatePlayerModel
import domain.models.PlayerColor
import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.player.CreatePlayerError
import domain.models.errors.player.DeletePlayerError
import domain.models.errors.player.EditPlayerError
import domain.models.errors.player.GetPlayerError
import domain.models.errors.player.GetPlayersError
import domain.repositories.PlayerRepository

class PlayerRepositoryImpl(
    database: ApplicationDatabase,
    private val logger : Logger
) : PlayerRepository {

    private val playerDao: PlayerDao = database.playerDao()

    override suspend fun getPlayers(): Resource<List<PlayerModel>, GetPlayersError> {
        return try {
            val playersEntity = playerDao.getPlayers().map {
                PlayerModel(
                    id = it.id!!,
                    name = it.name,
                    color = it.color,
                )
            }

            Resource.Success(playersEntity)
        }
        catch (e : Exception){
            logger.e(e)
            Resource.Error(GetPlayersError.UnknownError)
        }
    }

    override suspend fun createPlayer(player: CreatePlayerModel): Resource<PlayerModel, CreatePlayerError> {
        return try {
            val playerEntity = PlayerEntity(
                name = player.name,
                color = player.color
            )

            val id = playerDao.insertPlayer(playerEntity)

            return Resource.Success(PlayerModel(id = id, name = player.name, color = player.color))
        }
        catch (e : SQLiteException){
            logger.e(e)
            Resource.Error(CreatePlayerError.PlayerAlreadyExists)
        }
        catch (e : Exception){
            logger.e(e)
            Resource.Error(CreatePlayerError.UnknownError)
        }
    }

    override suspend fun deletePlayer(id: Long): Resource<Unit, DeletePlayerError> {
        return try {
            val player = playerDao.getPlayer(id)
            playerDao.deletePlayer(player.id!!)
            Resource.Success(Unit)
        }
        catch (e : NullPointerException){
            logger.e(e)
            Resource.Error(DeletePlayerError.PlayerNotFound(id))
        }
        catch (e : SQLiteException){
            logger.e(e)
            Resource.Error(DeletePlayerError.PlayerHasGames)
        }
        catch (e : Exception){
            logger.e(e)
            Resource.Error(DeletePlayerError.UnknownError)
        }
    }

    override suspend fun getPlayer(id: Long): Resource<PlayerModel, GetPlayerError> {
        return try {
            val player = playerDao.getPlayer(id)
            Resource.Success(PlayerModel(id = player.id!!, name = player.name, color = player.color))
        }
        catch (e : NullPointerException){
            logger.e(e)
            Resource.Error(GetPlayerError.PlayerNotFound)
        }
        catch (e : Exception){
            logger.e(e)
            Resource.Error(GetPlayerError.UnknownError)
        }
    }

    override suspend fun editPlayer(
        id: Long,
        name: String,
        color: PlayerColor
    ): Resource<PlayerModel, EditPlayerError> {
        return try {
            val player = playerDao.getPlayer(id)
            val editedPlayer = player.copy(name = name, color = color)
            playerDao.updatePlayer(editedPlayer)

            Resource.Success(PlayerModel(id = player.id!!, name = player.name, color = player.color))
        }
        catch (e : SQLiteException){
            logger.e(e)
            Resource.Error(EditPlayerError.NameAlreadyTaken)
        }
        catch (e : NullPointerException){
            logger.e(e)
            Resource.Error(EditPlayerError.PlayerNotFound)
        }
        catch (e : Exception){
            logger.e(e)
            Resource.Error(EditPlayerError.UnknownError)
        }
    }
}