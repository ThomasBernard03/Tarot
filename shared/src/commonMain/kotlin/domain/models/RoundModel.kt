package fr.thomasbernard03.tarot.domain.models

import java.util.Date

data class RoundModel(
    val id : Long,
    val finishedAt : Date,
    val taker: PlayerModel,
    val bid : Bid, // SMALL, GUARD, GUARD_WITHOUT, GUARD_AGAINST
    val oudlers : List<Oudler>,
    val points : Int, // Between 0 and 91
    val calledPlayer : PlayerModel? = null
)