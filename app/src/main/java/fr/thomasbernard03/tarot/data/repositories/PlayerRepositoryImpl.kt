package fr.thomasbernard03.tarot.data.repositories

import android.util.Log
import fr.thomasbernard03.tarot.data.local.dao.PlayerDao
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.errors.GetPlayersError
import fr.thomasbernard03.tarot.domain.repositories.PlayerRepository
import org.koin.java.KoinJavaComponent.get

class PlayerRepositoryImpl(
    private val playerDao: PlayerDao = get(PlayerDao::class.java)
) : PlayerRepository {
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
            Log.e(e.message, e.stackTraceToString())
            Resource.Error(GetPlayersError.UnknownError)
        }
    }
}