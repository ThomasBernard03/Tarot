package fr.thomasbernard03.tarot.domain.models

data class EditRoundModel(
    val id : Long,
    val taker: PlayerModel,
    val bid : Bid, // SMALL, GUARD, GUARD_WITHOUT, GUARD_AGAINST
    val oudlers : List<Oudler>,
    val points : Int, // Between 0 and 91
    val calledPlayer : PlayerModel? = null
)