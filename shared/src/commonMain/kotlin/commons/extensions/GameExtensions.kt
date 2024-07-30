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
            val pointGoal = when(round.oudlers.size){
                0 -> 56
                1 -> 51
                2 -> 41
                3 -> 36
                else -> throw IllegalArgumentException("Invalid number of oudlers")
            }

            val bidMultiplier = when(round.bid){
                Bid.SMALL -> 1
                Bid.GUARD -> 2
                Bid.GUARD_WITHOUT -> 4
                Bid.GUARD_AGAINST -> 6
            }

            val score = when {
                (round.points - pointGoal >= 0) -> (round.points - pointGoal + 25) * bidMultiplier
                else -> (round.points - pointGoal - 25) * bidMultiplier
            }

            // if player is a defender
            if (player != round.taker && player != round.calledPlayer){
                return@sumOf -score
            }

            val numberOfDefender = if (round.calledPlayer == null || round.taker == round.calledPlayer) players.size - 1 else players.size - 2
            val defenseScore = numberOfDefender * -score

            if (player == round.taker){
                return@sumOf -(defenseScore + if (round.calledPlayer != null && round.calledPlayer != player) score else 0)
            }
            // Else player is a partner
            else {
                return@sumOf score
            }
        }
    }
}