package domain.models

import kotlinx.datetime.LocalDateTime

data class RoundModel(
    val id : Long,
    val finishedAt : LocalDateTime,
    val taker: PlayerModel,
    val bid : Bid, // SMALL, GUARD, GUARD_WITHOUT, GUARD_AGAINST
    val oudlers : List<Oudler>,
    val points : Int, // Between 0 and 91
    val calledPlayer : PlayerModel? = null
)