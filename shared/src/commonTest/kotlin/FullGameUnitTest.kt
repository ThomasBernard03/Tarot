import commons.extensions.LocalDateTimeNow
import commons.extensions.calculateScore
import domain.models.Bid
import domain.models.GameModel
import domain.models.Oudler
import domain.models.PlayerColor
import domain.models.PlayerModel
import domain.models.RoundModel
import kotlin.test.Test
import kotlin.test.assertEquals

class FullGameUnitTest {
    @Test
    fun fivePlayersGame(){
        val player1 = PlayerModel(id = 1, name = "Player 1", color = PlayerColor.BLUE)
        val player2 = PlayerModel(id = 2, name = "Player 2", color = PlayerColor.RED)
        val player3 = PlayerModel(id = 3, name = "Player 3", color = PlayerColor.GREEN)
        val player4 = PlayerModel(id = 4, name = "Player 4", color = PlayerColor.YELLOW)
        val player5 = PlayerModel(id = 5, name = "Player 5", color = PlayerColor.PINK)

        val rounds = mutableListOf(
            RoundModel(
                id = 1,
                taker = player1,
                bid = Bid.SMALL,
                oudlers = listOf(),
                points = 41,
                calledPlayer = player2,
                finishedAt = LocalDateTimeNow()
            )
        )

        // At first :
        // | Player 1 | Player 2 | Player 3 | Player 4 | Player 5 |
        // |    -80   |   -40    |    40    |    40    |    40    |
        var game = GameModel(id = 1, startedAt = LocalDateTimeNow(), players = listOf(player1, player2, player3, player4, player5), rounds = rounds)
        val playersScoreRound1 = game.calculateScore()
        var player1Score = playersScoreRound1.find { it.first == player1 }?.second
        var player2Score = playersScoreRound1.find { it.first == player2 }?.second
        var player3Score = playersScoreRound1.find { it.first == player3 }?.second
        var player4Score = playersScoreRound1.find { it.first == player4 }?.second
        var player5Score = playersScoreRound1.find { it.first == player5 }?.second

        assertEquals(-80, player1Score)
        assertEquals(-40, player2Score)
        assertEquals(40, player3Score)
        assertEquals(40, player4Score)
        assertEquals(40, player5Score)


        rounds.add(
            RoundModel(
                id = 2,
                taker = player3,
                bid = Bid.GUARD,
                oudlers = listOf(Oudler.EXCUSE, Oudler.PETIT),
                points = 65,
                calledPlayer = player4,
                finishedAt = LocalDateTimeNow()
            )
        )


        game = game.copy(rounds = rounds)
        val playersScoreRound2 = game.calculateScore()
        player1Score = playersScoreRound2.find { it.first == player1 }?.second
        player2Score = playersScoreRound2.find { it.first == player2 }?.second
        player3Score = playersScoreRound2.find { it.first == player3 }?.second
        player4Score = playersScoreRound2.find { it.first == player4 }?.second
        player5Score = playersScoreRound2.find { it.first == player5 }?.second

        assertEquals(-178, player1Score)
        assertEquals(-138, player2Score)
        assertEquals(236, player3Score)
        assertEquals(138, player4Score)
        assertEquals(-58, player5Score)


        rounds.add(
            RoundModel(
                id = 3,
                taker = player1,
                bid = Bid.GUARD,
                oudlers = listOf(Oudler.EXCUSE, Oudler.PETIT, Oudler.GRAND),
                points = 61,
                calledPlayer = player4,
                finishedAt = LocalDateTimeNow()
            )
        )


        game = game.copy(rounds = rounds)
        val playersScoreRound3 = game.calculateScore()
        player1Score = playersScoreRound3.find { it.first == player1 }?.second
        player2Score = playersScoreRound3.find { it.first == player2 }?.second
        player3Score = playersScoreRound3.find { it.first == player3 }?.second
        player4Score = playersScoreRound3.find { it.first == player4 }?.second
        player5Score = playersScoreRound3.find { it.first == player5 }?.second

        assertEquals(22, player1Score)
        assertEquals(-238, player2Score)
        assertEquals(136, player3Score)
        assertEquals(238, player4Score)
        assertEquals(-158, player5Score)

        rounds.add(
            RoundModel(
                id = 4,
                taker = player2,
                bid = Bid.GUARD_WITHOUT,
                oudlers = listOf(Oudler.EXCUSE, Oudler.PETIT),
                points = 70,
                calledPlayer = player5,
                finishedAt = LocalDateTimeNow()
            )
        )


        game = game.copy(rounds = rounds)
        val playersScoreRound4 = game.calculateScore()
        player1Score = playersScoreRound4.find { it.first == player1 }?.second
        player2Score = playersScoreRound4.find { it.first == player2 }?.second
        player3Score = playersScoreRound4.find { it.first == player3 }?.second
        player4Score = playersScoreRound4.find { it.first == player4 }?.second
        player5Score = playersScoreRound4.find { it.first == player5 }?.second

        assertEquals(-194, player1Score)
        assertEquals(194, player2Score)
        assertEquals(-80, player3Score)
        assertEquals(22, player4Score)
        assertEquals(58, player5Score)

        rounds.add(
            RoundModel(
                id = 5,
                taker = player3,
                bid = Bid.GUARD,
                oudlers = listOf(Oudler.EXCUSE),
                points = 62,
                calledPlayer = player3,
                finishedAt = LocalDateTimeNow()
            )
        )

        game = game.copy(rounds = rounds)
        val playersScoreRound5 = game.calculateScore()
        player1Score = playersScoreRound5.find { it.first == player1 }?.second
        player2Score = playersScoreRound5.find { it.first == player2 }?.second
        player3Score = playersScoreRound5.find { it.first == player3 }?.second
        player4Score = playersScoreRound5.find { it.first == player4 }?.second
        player5Score = playersScoreRound5.find { it.first == player5 }?.second

        assertEquals(-266, player1Score)
        assertEquals(122, player2Score)
        assertEquals(208, player3Score)
        assertEquals(-50, player4Score)
        assertEquals(-14, player5Score)


        rounds.add(
            RoundModel(
                id = 6,
                taker = player1,
                bid = Bid.GUARD_AGAINST,
                oudlers = listOf(Oudler.EXCUSE, Oudler.PETIT),
                points = 8,
                calledPlayer = player4,
                finishedAt = LocalDateTimeNow()
            )
        )

        game = game.copy(rounds = rounds)
        val playersScoreRound6 = game.calculateScore()
        player1Score = playersScoreRound6.find { it.first == player1 }?.second
        player2Score = playersScoreRound6.find { it.first == player2 }?.second
        player3Score = playersScoreRound6.find { it.first == player3 }?.second
        player4Score = playersScoreRound6.find { it.first == player4 }?.second
        player5Score = playersScoreRound6.find { it.first == player5 }?.second

        assertEquals(-962, player1Score)
        assertEquals(470, player2Score)
        assertEquals(556, player3Score)
        assertEquals(-398, player4Score)
        assertEquals(334, player5Score)
    }

    @Test
    fun fourPlayersGame(){
        val player1 = PlayerModel(id = 1, name = "Player 1", color = PlayerColor.GREEN)
        val player2 = PlayerModel(id = 2, name = "Player 2", color = PlayerColor.BLUE)
        val player3 = PlayerModel(id = 3, name = "Player 3", color = PlayerColor.RED)
        val player4 = PlayerModel(id = 3, name = "Player 4", color = PlayerColor.ORANGE)

        val rounds = mutableListOf(
            RoundModel(
                id = 1,
                taker = player1,
                bid = Bid.SMALL,
                oudlers = listOf(),
                points = 41,
                finishedAt = LocalDateTimeNow()
            )
        )

        var game = GameModel(id = 1, startedAt = LocalDateTimeNow(), players = listOf(player1, player2, player3, player4), rounds = rounds)
        val playersScoreRound1 = game.calculateScore()
        var player1Score = playersScoreRound1.find { it.first == player1 }?.second
        var player2Score = playersScoreRound1.find { it.first == player2 }?.second
        var player3Score = playersScoreRound1.find { it.first == player3 }?.second
        var player4Score = playersScoreRound1.find { it.first == player4 }?.second

        assertEquals(-120, player1Score)
        assertEquals(40, player2Score)
        assertEquals(40, player3Score)
        assertEquals(40, player4Score)
    }


    @Test
    fun threePlayersGame(){
        val player1 = PlayerModel(id = 1, name = "Player 1", color = PlayerColor.GREEN)
        val player2 = PlayerModel(id = 2, name = "Player 2", color = PlayerColor.BLUE)
        val player3 = PlayerModel(id = 3, name = "Player 3", color = PlayerColor.RED)

        val rounds = mutableListOf(
            RoundModel(
                id = 1,
                taker = player1,
                bid = Bid.SMALL,
                oudlers = listOf(),
                points = 41,
                finishedAt = LocalDateTimeNow()
            )
        )

        var game = GameModel(id = 1, startedAt = LocalDateTimeNow(), players = listOf(player1, player2, player3), rounds = rounds)
        val playersScoreRound1 = game.calculateScore()
        var player1Score = playersScoreRound1.find { it.first == player1 }?.second
        var player2Score = playersScoreRound1.find { it.first == player2 }?.second
        var player3Score = playersScoreRound1.find { it.first == player3 }?.second

        assertEquals(-80, player1Score)
        assertEquals(40, player2Score)
        assertEquals(40, player3Score)


        rounds.add(
            RoundModel(
                id = 2,
                taker = player2,
                bid = Bid.GUARD,
                oudlers = listOf(Oudler.EXCUSE),
                points = 55,
                finishedAt = LocalDateTimeNow()
            )
        )


        game = game.copy(rounds = rounds)
        val playersScoreRound2 = game.calculateScore()
        player1Score = playersScoreRound2.find { it.first == player1 }?.second
        player2Score = playersScoreRound2.find { it.first == player2 }?.second
        player3Score = playersScoreRound2.find { it.first == player3 }?.second

        assertEquals(-138, player1Score)
        assertEquals(156, player2Score)
        assertEquals(-18, player3Score)


        rounds.add(
            RoundModel(
                id = 3,
                taker = player3,
                bid = Bid.GUARD_WITHOUT,
                oudlers = listOf(Oudler.EXCUSE, Oudler.PETIT),
                points = 71,
                finishedAt = LocalDateTimeNow()
            )
        )


        game = game.copy(rounds = rounds)
        val playersScoreRound3 = game.calculateScore()
        player1Score = playersScoreRound3.find { it.first == player1 }?.second
        player2Score = playersScoreRound3.find { it.first == player2 }?.second
        player3Score = playersScoreRound3.find { it.first == player3 }?.second

        assertEquals(-358, player1Score)
        assertEquals(-64, player2Score)
        assertEquals(422, player3Score)

        rounds.add(
            RoundModel(
                id = 4,
                taker = player3,
                bid = Bid.GUARD_AGAINST,
                oudlers = listOf(Oudler.EXCUSE, Oudler.PETIT, Oudler.GRAND),
                points = 33,
                finishedAt = LocalDateTimeNow()
            )
        )


        game = game.copy(rounds = rounds)
        val playersScoreRound4 = game.calculateScore()
        player1Score = playersScoreRound4.find { it.first == player1 }?.second
        player2Score = playersScoreRound4.find { it.first == player2 }?.second
        player3Score = playersScoreRound4.find { it.first == player3 }?.second

        assertEquals(-190, player1Score)
        assertEquals(104, player2Score)
        assertEquals(86, player3Score)
    }
}