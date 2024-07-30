package commons.extensions

import commons.getBidMultiplier
import commons.getPointToReach
import domain.models.Bid
import domain.models.RoundModel

fun RoundModel.calculateTakerScore(numberOfPlayers : Int) : Int {
    val pointGoal = getPointToReach(oudlers.size)
    val bidMultiplier = getBidMultiplier(bid)

    val score = when {
        (points - pointGoal >= 0) -> (points - pointGoal + 25) * bidMultiplier
        else -> (points - pointGoal - 25) * bidMultiplier
    }

    val numberOfDefender = if (calledPlayer == null || taker == calledPlayer) numberOfPlayers - 1 else numberOfPlayers - 2
    val defenseScore = numberOfDefender * -score

    return -(defenseScore + if (calledPlayer != null && calledPlayer != taker) score else 0)
}

fun RoundModel.calculateDefenderScore() : Int {
    val pointGoal = getPointToReach(oudlers.size)
    val bidMultiplier = getBidMultiplier(bid)

    val score = when {
        (points - pointGoal >= 0) -> (points - pointGoal + 25) * bidMultiplier
        else -> (points - pointGoal - 25) * bidMultiplier
    }

    return -score
}

fun RoundModel.calculatePartnerScore() : Int {
    return -calculateDefenderScore()
}