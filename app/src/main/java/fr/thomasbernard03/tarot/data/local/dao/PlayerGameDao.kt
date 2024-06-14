package fr.thomasbernard03.tarot.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import fr.thomasbernard03.tarot.data.local.entities.GameEntity
import fr.thomasbernard03.tarot.data.local.entities.PlayerEntity
import fr.thomasbernard03.tarot.data.local.entities.PlayerGameEntity
import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.Game
import fr.thomasbernard03.tarot.domain.models.Player
import java.util.Date

@Dao
interface PlayerGameDao {
    @Insert
    suspend fun addPlayerToGame(playersGameEntity: List<PlayerGameEntity>) : List<Long>

    @Query("""
        SELECT PlayerEntity.*
        FROM PlayerEntity
        INNER JOIN PlayerGameEntity ON PlayerEntity.id = PlayerGameEntity.playerId
        WHERE PlayerGameEntity.gameId = :gameId
    """)
    suspend fun getPlayersForGame(gameId: Long) : List<PlayerEntity>



    @Insert
    suspend fun insertPlayers(players: List<PlayerEntity>) : List<Long>

    @Insert
    suspend fun createGame(game : GameEntity) : Long

    @Transaction
    suspend fun createGameAndAddPlayer(
        players : List<CreatePlayerModel>
    ) : Game {
        // 1) Insert players
        val playersIds = insertPlayers(players.map { PlayerEntity(name = it.name, color = it.color) })
        val playersWithId = players.zip(playersIds).map { Player(id = it.second, name = it.first.name, color = it.first.color) }


        // 2) Create a game
        var game = GameEntity(startedAt = Date())
        val gameId = createGame(game)
        game = game.copy(id = gameId)

        // 3) Insert all players in the game
        val playerGameEntities = playersWithId.map { PlayerGameEntity(playerId = it.id, gameId = gameId) }
        addPlayerToGame(playerGameEntities)
        return Game(
            id = gameId,
            startedAt = game.startedAt,
            players = playersWithId.map { Player(id = it.id, name = it.name, color = it.color) })
    }
}