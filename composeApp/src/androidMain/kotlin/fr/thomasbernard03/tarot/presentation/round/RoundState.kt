package fr.thomasbernard03.tarot.presentation.round

import fr.thomasbernard03.tarot.domain.models.Bid
import fr.thomasbernard03.tarot.domain.models.Oudler
import fr.thomasbernard03.tarot.domain.models.PlayerModel

data class RoundState(
    val taker : PlayerModel? = null,
    val calledPlayer : PlayerModel? = null,


    val score : Int = 0,
    val numberOfPoints : Int = 0,
    val players : List<PlayerModel> = listOf(),
    val bid : Bid? = null,
    val oudlers : List<Oudler> = listOf()
)