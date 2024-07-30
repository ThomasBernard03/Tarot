package commons.extensions

import domain.models.Bid
import domain.models.RoundModel

private fun getPointToReach(oudlers: Int): Int {
    return when (oudlers) {
        0 -> 56
        1 -> 51
        2 -> 41
        3 -> 36
        else -> throw IllegalArgumentException("Invalid number of oudlers")
    }
}

private fun getBidMultiplier(bid: Bid): Int {
    return when (bid) {
        Bid.SMALL -> 1
        Bid.GUARD -> 2
        Bid.GUARD_WITHOUT -> 4
        Bid.GUARD_AGAINST -> 6
    }
}

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