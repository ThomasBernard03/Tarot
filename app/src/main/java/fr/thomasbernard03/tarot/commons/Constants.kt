package fr.thomasbernard03.tarot.commons

import androidx.annotation.IntRange
import androidx.compose.ui.unit.dp
import fr.thomasbernard03.tarot.domain.models.Bid

val LargePadding = 16.dp
val MediumPadding = 8.dp
val SmallPadding = 4.dp

const val MAX_PLAYER = 5
const val PLAYER_NAME_MAX_LENGTH = 20

fun calculateTakerScore(
    @IntRange(from = 0, to = 91) points: Int,
    bid: Bid,
    @IntRange(from = 0, to = 3) oudlers: Int,
    @IntRange(from = 3, to = 5) playerCount: Int,
    isCalledPlayerTaker: Boolean
): Int {
    // Thresholds for the number of points needed based on the number of oudlers
    val bidMultiplier = when(bid){
        Bid.SMALL -> 1
        Bid.GUARD -> 2
        Bid.GUARD_WITHOUT -> 4
        Bid.GUARD_AGAINST -> 6
    }

    val pointGoal = when(oudlers){
        0 -> 56
        1 -> 51
        2 -> 41
        3 -> 36
        else -> throw IllegalArgumentException("Invalid number of oudlers")
    }

    val score = when {
        (points - pointGoal >= 0) -> (points - pointGoal + 25) * bidMultiplier
        else -> (points - pointGoal - 25) * bidMultiplier
    }

    return score * 2
}

fun calculatePartnerScore(takerScore: Int): Int {
    return takerScore / 2
}

fun calculateDefenderScore(takerScore: Int, numberOfDefenders: Int): Int {
    return -takerScore / numberOfDefenders
}
