package fr.thomasbernard03.tarot.domain.models

data class TakerModel(
    val player : PlayerModel,
    val bid : Bid, // SMALL, GUARD, GUARD_WITHOUT, GUARD_AGAINST
    val oudlers : List<Oudler>,
    val points : Int // Between 0 and 91
)