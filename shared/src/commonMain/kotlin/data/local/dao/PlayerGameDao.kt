package data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import data.local.entities.GameEntity
import data.local.entities.PlayerEntity
import data.local.entities.PlayerGameEntity

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
    suspend fun createGame(game : GameEntity) : Long

    @Transaction
    suspend fun createGameAndLinkPlayers(
        players : List<PlayerModel>
    ) : GameModel {
        // 1) Create a game
        var game = GameEntity(startedAt = Date())
        val gameId = createGame(game)
        game = game.copy(id = gameId)

        // 2) Insert all players in the game
        val playerGameEntities = players.map { PlayerGameEntity(playerId = it.id, gameId = gameId) }
        addPlayerToGame(playerGameEntities)
        return GameModel(
            id = gameId,
            startedAt = game.startedAt,
            players = players,
            rounds = listOf()
        )
    }
}