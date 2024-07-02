package fr.thomasbernard03.tarot.presentation.round

import fr.thomasbernard03.tarot.domain.models.Bid
import fr.thomasbernard03.tarot.domain.models.Oudler
import fr.thomasbernard03.tarot.domain.models.PlayerModel

sealed class RoundEvent {
    data class OnGetPlayers(val gameId: Long) : RoundEvent()
    data class OnGetRound(val gameId: Long, val roundId : Long) : RoundEvent()


    data object OnGoBack : RoundEvent()

    data class OnCreateRound(val gameId : Long, val taker : PlayerModel, val bid: Bid, val oudlers : List<Oudler>, val points : Int, val calledPlayer : PlayerModel?) : RoundEvent()
    data class OnEditRound(val roundId : Long, val taker : PlayerModel, val bid: Bid, val oudlers : List<Oudler>, val points : Int, val calledPlayer : PlayerModel?) : RoundEvent()


    data class OnTakerChanged(val player : PlayerModel?) : RoundEvent()
    data class OnCalledPlayerChanged(val player : PlayerModel?) : RoundEvent()
    data class OnBidChanged(val bid : Bid?) : RoundEvent()
    data class OnScoreChanged(val score : Int) : RoundEvent()
    data class OnNumberOfPointsChanged(val points : Int) : RoundEvent()
    data class OnOudlerSelected(val oudler : Oudler) : RoundEvent()
}