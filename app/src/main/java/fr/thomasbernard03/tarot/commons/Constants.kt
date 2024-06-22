package fr.thomasbernard03.tarot.commons

import androidx.annotation.IntRange
import androidx.compose.ui.unit.dp
import fr.thomasbernard03.tarot.domain.models.Bid

val LargePadding = 16.dp
val MediumPadding = 8.dp


const val MAX_PLAYER = 5

fun calculateTakerScore(
    @IntRange(from = 0, to = 91) points: Int,
    bid: Bid,
    @IntRange(from = 0, to = 3) oudlers: Int,
    @IntRange(from = 3, to = 5) playerCount: Int,
    isCalledPlayerTaker: Boolean
): Int {
    val pointsToAchieve = when (oudlers) {
        0 -> 56
        1 -> 51
        2 -> 41
        3 -> 36
        else -> throw IllegalArgumentException("Number of oudlers must be between 0 and 3")
    }

    val pointDifference = points - pointsToAchieve
    val baseScore = if (pointDifference >= 0) pointDifference + 25 else pointDifference - 25

    val multiplier = when (bid) {
        Bid.SMALL -> 1
        Bid.GUARD -> 2
        Bid.GUARD_WITHOUT -> 4
        Bid.GUARD_AGAINST -> 6
    }

    var takerScore = baseScore * multiplier

    // Adjust score based on the number of players
    if (playerCount == 5) {
        takerScore = (takerScore * 2.5).toInt()
    }

    return takerScore
}

fun calculatePartnerScore(takerScore: Int): Int {
    return takerScore / 2
}

fun calculateDefenderScore(takerScore: Int, numberOfDefenders: Int): Int {
    return -takerScore / numberOfDefenders
}