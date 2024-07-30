package fr.thomasbernard03.tarot.presentation.round

import domain.models.Bid
import domain.models.Oudler
import domain.models.PlayerModel

data class RoundState(
    val taker : PlayerModel? = null,
    val calledPlayer : PlayerModel? = null,


    val score : Int = 0,
    val numberOfPoints : Int = 0,
    val players : List<PlayerModel> = listOf(),
    val bid : Bid? = null,
    val oudlers : List<Oudler> = listOf()
)