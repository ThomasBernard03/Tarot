package commons.extensions

import commons.calculateDefenderScore
import commons.calculatePartnerScore
import commons.calculateTakerScore
import domain.models.GameModel
import domain.models.PlayerModel

fun GameModel.calculateScore() : List<Pair<PlayerModel, Int>>{
    return players.map { player ->
        player to rounds.sumOf {
            val takerScore = calculateTakerScore(
                points = it.points,
                bid = it.bid,
                oudlers = it.oudlers.size,
                calledHimSelf = it.calledPlayer == it.taker
            )

            val partnerScore = calculatePartnerScore(takerScore)

            if (it.taker == player)
                takerScore
            else if (it.calledPlayer == player)
                partnerScore
            else {
                if (it.taker == it.calledPlayer){
                    calculateDefenderScore(takerScore, 4)
                }
                else {
                    val attackScore = takerScore + partnerScore
                    calculateDefenderScore(attackScore, 3)
                }
            }
        }
    }
}