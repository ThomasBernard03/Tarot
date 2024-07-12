package fr.thomasbernard03.tarot.presentation.round

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.models.Bid
import domain.models.EditRoundModel
import domain.models.Oudler
import domain.models.PlayerModel
import domain.models.Resource
import domain.usecases.game.GetGameUseCase
import domain.usecases.round.CreateRoundUseCase
import domain.usecases.round.EditRoundUseCase
import domain.usecases.round.GetRoundUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoundViewModel(
    private val getGameUseCase: GetGameUseCase,
    private val getRoundUseCase: GetRoundUseCase,
    private val createRoundUseCase: CreateRoundUseCase,
    private val editRoundUseCase: EditRoundUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(RoundState())
    val state: StateFlow<RoundState> = _state.asStateFlow()

    fun onEvent(event : RoundEvent){
        when(event){
            is RoundEvent.OnGoBack -> Unit
            is RoundEvent.OnGetPlayers -> onGetPlayers(event.gameId)
            is RoundEvent.OnTakerChanged -> _state.update { it.copy(taker = event.player) }
            is RoundEvent.OnBidChanged -> _state.update { it.copy(bid = event.bid) }
            is RoundEvent.OnNumberOfPointsChanged -> onNumberOfPointsChanged(event.points)
            is RoundEvent.OnScoreChanged -> _state.update { it.copy(score = event.score) }
            is RoundEvent.OnOudlerSelected -> onOudlerSelected(event.oudler)
            is RoundEvent.OnCalledPlayerChanged -> _state.update { it.copy(calledPlayer = event.player) }
            is RoundEvent.OnCreateRound -> onCreateRound(event.gameId, event.taker, event.bid, event.oudlers, event.points, event.calledPlayer)
            is RoundEvent.OnGetRound -> onGetRound(event.gameId, event.roundId)
            is RoundEvent.OnEditRound -> onEditRound(event.roundId, event.taker, event.bid, event.oudlers, event.points, event.calledPlayer)
        }
    }

    private fun onCreateRound(gameId : Long, taker : PlayerModel, bid : Bid, oudlers : List<Oudler>, points: Int, calledPlayer : PlayerModel?) {
        viewModelScope.launch {
            when(val result = createRoundUseCase(gameId, taker, calledPlayer, bid, oudlers, points)){
                is Resource.Success -> {
//                    navigationHelper.goBack()
                }
                is Resource.Error -> {
                }
            }
        }
    }

    private fun onEditRound(roundId : Long, taker : PlayerModel, bid : Bid, oudlers : List<Oudler>, points: Int, calledPlayer : PlayerModel?) {
        viewModelScope.launch {
            val round = EditRoundModel(id = roundId, taker = taker, bid = bid, oudlers = oudlers, points = points, calledPlayer = calledPlayer)
            when(val result = editRoundUseCase(round)){
                is Resource.Success -> {
//                    navigationHelper.goBack()
                }
                is Resource.Error -> {
                }
            }
        }
    }

    private fun onGetPlayers(gameId : Long) {
        viewModelScope.launch {
            when(val result = getGameUseCase(gameId)){
                is Resource.Success -> {
                    _state.update { it.copy(players = result.data.players) }
                }
                is Resource.Error -> {
                }
            }
        }
    }

    private fun onNumberOfPointsChanged(points : Int) {
        if (points < 0) return
        else if (points > 91) return
        _state.update { it.copy(numberOfPoints = points) }
    }

    private fun onOudlerSelected(oudler : Oudler) {
        if (_state.value.oudlers.contains(oudler)){
            _state.update { it.copy(oudlers = _state.value.oudlers - oudler) }
        } else {
            _state.update { it.copy(oudlers = _state.value.oudlers + oudler) }
        }
    }

    private fun onGetRound(gameId: Long, roundId: Long) {
        viewModelScope.launch {
            when(val result = getRoundUseCase(gameId, roundId)){
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            taker = result.data.taker,
                            bid = result.data.bid,
                            oudlers = result.data.oudlers,
                            calledPlayer = result.data.calledPlayer,
                            numberOfPoints = result.data.points
                        )
                    }
                }
                is Resource.Error -> {
                }
            }
        }
    }
}