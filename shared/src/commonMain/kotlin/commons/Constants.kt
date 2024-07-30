package commons

import androidx.annotation.IntRange
import domain.models.Bid
import domain.models.PlayerModel

const val PLAYER_NAME_MAX_LENGTH = 20

fun calculateTakerScore(
    taker : PlayerModel,
    calledPlayer : PlayerModel?,
    @IntRange(from = 3, to = 5) numberOfPlayers: Int,
    @IntRange(from = 0, to = 3) oudlers: Int,
    bid : Bid,
    points : Int,
) : Int {
    val pointGoal = getPointToReach(oudlers)
    val bidMultiplier = getBidMultiplier(bid)

    val score = when {
        (points - pointGoal >= 0) -> (points - pointGoal + 25) * bidMultiplier
        else -> (points - pointGoal - 25) * bidMultiplier
    }

    val numberOfDefender = if (calledPlayer == null || taker == calledPlayer) numberOfPlayers - 1 else numberOfPlayers - 2
    val defenseScore = numberOfDefender * -score

    return -(defenseScore + if (calledPlayer != null && calledPlayer != taker) score else 0)
}


fun getPointToReach(oudlers: Int): Int {
    return when (oudlers) {
        0 -> 56
        1 -> 51
        2 -> 41
        3 -> 36
        else -> throw IllegalArgumentException("Invalid number of oudlers")
    }
}

fun getBidMultiplier(bid: Bid): Int {
    return when (bid) {
        Bid.SMALL -> 1
        Bid.GUARD -> 2
        Bid.GUARD_WITHOUT -> 4
        Bid.GUARD_AGAINST -> 6
    }
}
