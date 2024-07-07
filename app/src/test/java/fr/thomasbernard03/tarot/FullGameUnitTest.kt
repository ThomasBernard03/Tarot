package fr.thomasbernard03.tarot

import fr.thomasbernard03.tarot.commons.extensions.calculateScore
import fr.thomasbernard03.tarot.domain.models.Bid
import fr.thomasbernard03.tarot.domain.models.GameModel
import fr.thomasbernard03.tarot.domain.models.Oudler
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.RoundModel
import junit.framework.TestCase
import org.junit.Test
import java.util.Date

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
                finishedAt = Date()
            )
        )

        // At first :
        // | Player 1 | Player 2 | Player 3 | Player 4 | Player 5 |
        // |    -80   |   -40    |    40    |    40    |    40    |
        var game = GameModel(id = 1, startedAt = Date(), players = listOf(player1, player2, player3, player4, player5), rounds = rounds)
        val playersScoreRound1 = game.calculateScore()
        var player1Score = playersScoreRound1.find { it.first == player1 }?.second
        var player2Score = playersScoreRound1.find { it.first == player2 }?.second
        var player3Score = playersScoreRound1.find { it.first == player3 }?.second
        var player4Score = playersScoreRound1.find { it.first == player4 }?.second
        var player5Score = playersScoreRound1.find { it.first == player5 }?.second

        TestCase.assertEquals(-80, player1Score)
        TestCase.assertEquals(-40, player2Score)
        TestCase.assertEquals(40, player3Score)
        TestCase.assertEquals(40, player4Score)
        TestCase.assertEquals(40, player5Score)


        rounds.add(
            RoundModel(
                id = 2,
                taker = player3,
                bid = Bid.GUARD,
                oudlers = listOf(Oudler.EXCUSE, Oudler.PETIT),
                points = 65,
                calledPlayer = player4,
                finishedAt = Date()
            )
        )


        game = game.copy(rounds = rounds)
        val playersScoreRound2 = game.calculateScore()
        player1Score = playersScoreRound2.find { it.first == player1 }?.second
        player2Score = playersScoreRound2.find { it.first == player2 }?.second
        player3Score = playersScoreRound2.find { it.first == player3 }?.second
        player4Score = playersScoreRound2.find { it.first == player4 }?.second
        player5Score = playersScoreRound2.find { it.first == player5 }?.second

        TestCase.assertEquals(-178, player1Score)
        TestCase.assertEquals(-138, player2Score)
        TestCase.assertEquals(236, player3Score)
        TestCase.assertEquals(138, player4Score)
        TestCase.assertEquals(-58, player5Score)


        rounds.add(
            RoundModel(
                id = 3,
                taker = player1,
                bid = Bid.GUARD,
                oudlers = listOf(Oudler.EXCUSE, Oudler.PETIT, Oudler.GRAND),
                points = 61,
                calledPlayer = player4,
                finishedAt = Date()
            )
        )


        game = game.copy(rounds = rounds)
        val playersScoreRound3 = game.calculateScore()
        player1Score = playersScoreRound3.find { it.first == player1 }?.second
        player2Score = playersScoreRound3.find { it.first == player2 }?.second
        player3Score = playersScoreRound3.find { it.first == player3 }?.second
        player4Score = playersScoreRound3.find { it.first == player4 }?.second
        player5Score = playersScoreRound3.find { it.first == player5 }?.second

        TestCase.assertEquals(22, player1Score)
        TestCase.assertEquals(-238, player2Score)
        TestCase.assertEquals(136, player3Score)
        TestCase.assertEquals(238, player4Score)
        TestCase.assertEquals(-158, player5Score)

        rounds.add(
            RoundModel(
                id = 4,
                taker = player2,
                bid = Bid.GUARD_WITHOUT,
                oudlers = listOf(Oudler.EXCUSE, Oudler.PETIT),
                points = 70,
                calledPlayer = player5,
                finishedAt = Date()
            )
        )


        game = game.copy(rounds = rounds)
        val playersScoreRound4 = game.calculateScore()
        player1Score = playersScoreRound4.find { it.first == player1 }?.second
        player2Score = playersScoreRound4.find { it.first == player2 }?.second
        player3Score = playersScoreRound4.find { it.first == player3 }?.second
        player4Score = playersScoreRound4.find { it.first == player4 }?.second
        player5Score = playersScoreRound4.find { it.first == player5 }?.second

        TestCase.assertEquals(-194, player1Score)
        TestCase.assertEquals(194, player2Score)
        TestCase.assertEquals(-80, player3Score)
        TestCase.assertEquals(22, player4Score)
        TestCase.assertEquals(58, player5Score)

        rounds.add(
            RoundModel(
                id = 5,
                taker = player3,
                bid = Bid.GUARD,
                oudlers = listOf(Oudler.EXCUSE),
                points = 62,
                calledPlayer = player3,
                finishedAt = Date()
            )
        )

        game = game.copy(rounds = rounds)
        val playersScoreRound5 = game.calculateScore()
        player1Score = playersScoreRound5.find { it.first == player1 }?.second
        player2Score = playersScoreRound5.find { it.first == player2 }?.second
        player3Score = playersScoreRound5.find { it.first == player3 }?.second
        player4Score = playersScoreRound5.find { it.first == player4 }?.second
        player5Score = playersScoreRound5.find { it.first == player5 }?.second

        TestCase.assertEquals(-266, player1Score)
        TestCase.assertEquals(122, player2Score)
        TestCase.assertEquals(208, player3Score)
        TestCase.assertEquals(-50, player4Score)
        TestCase.assertEquals(-14, player5Score)


        rounds.add(
            RoundModel(
                id = 6,
                taker = player1,
                bid = Bid.GUARD_AGAINST,
                oudlers = listOf(Oudler.EXCUSE, Oudler.PETIT),
                points = 8,
                calledPlayer = player4,
                finishedAt = Date()
            )
        )

        game = game.copy(rounds = rounds)
        val playersScoreRound6 = game.calculateScore()
        player1Score = playersScoreRound6.find { it.first == player1 }?.second
        player2Score = playersScoreRound6.find { it.first == player2 }?.second
        player3Score = playersScoreRound6.find { it.first == player3 }?.second
        player4Score = playersScoreRound6.find { it.first == player4 }?.second
        player5Score = playersScoreRound6.find { it.first == player5 }?.second

        TestCase.assertEquals(-962, player1Score)
        TestCase.assertEquals(470, player2Score)
        TestCase.assertEquals(556, player3Score)
        TestCase.assertEquals(-398, player4Score)
        TestCase.assertEquals(334, player5Score)
    }
}