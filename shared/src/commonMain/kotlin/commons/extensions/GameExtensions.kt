package commons.extensions

import commons.calculateDefenderScore
import commons.calculatePartnerScore
import commons.calculateTakerScore
import domain.models.Bid
import domain.models.GameModel
import domain.models.PlayerModel

fun GameModel.calculateScore() : List<Pair<PlayerModel, Int>>{
    return players.map { player ->
        player to rounds.sumOf { round ->
            when (player) {
                round.taker -> {
                    return@sumOf round.calculateTakerScore(players.size)
                }
                round.calledPlayer -> {
                    return@sumOf round.calculatePartnerScore()
                }
                else -> {
                    return@sumOf round.calculateDefenderScore()
                }
            }
        }
    }
}