package fr.thomasbernard03.tarot.presentation.round

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.thomasbernard03.tarot.commons.helpers.NavigationHelper
import fr.thomasbernard03.tarot.domain.models.Bid
import fr.thomasbernard03.tarot.domain.models.Oudler
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.TakerModel
import fr.thomasbernard03.tarot.domain.usecases.CreateGameUseCase
import fr.thomasbernard03.tarot.domain.usecases.CreateRoundUseCase
import fr.thomasbernard03.tarot.domain.usecases.GetGameUseCase
import fr.thomasbernard03.tarot.presentation.history.HistoryEvent
import fr.thomasbernard03.tarot.presentation.history.HistoryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.get

class RoundViewModel(
    private val getGameUseCase: GetGameUseCase = GetGameUseCase(),
    private val createRoundUseCase: CreateRoundUseCase = CreateRoundUseCase(),
    private val navigationHelper: NavigationHelper = get(NavigationHelper::class.java)
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
            is RoundEvent.OnCreateRound -> onCreateRound(event.gameId, event.taker, event.bid, event.oudlers, event.points)
        }
    }

    private fun onCreateRound(gameId : Long, taker : PlayerModel, bid : Bid, oudlers : List<Oudler>, points: Int){
        viewModelScope.launch {
            when(val result = createRoundUseCase(gameId, taker, null, bid, oudlers, points)){
                is Resource.Success -> {
                    navigationHelper.goBack()
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
}