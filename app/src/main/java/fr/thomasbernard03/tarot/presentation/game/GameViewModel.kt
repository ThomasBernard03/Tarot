package fr.thomasbernard03.tarot.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.thomasbernard03.tarot.domain.models.Bid
import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.Oudler
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.usecases.CreateGameUseCase
import fr.thomasbernard03.tarot.domain.usecases.FinishGameUseCase
import fr.thomasbernard03.tarot.domain.usecases.GetCurrentGameUseCase
import fr.thomasbernard03.tarot.domain.usecases.GetPlayersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    private val createGameUseCase: CreateGameUseCase = CreateGameUseCase(),
    private val getCurrentGameUseCase: GetCurrentGameUseCase = GetCurrentGameUseCase(),
    private val getPlayersUseCase: GetPlayersUseCase = GetPlayersUseCase(),
    private val finishGameUseCase: FinishGameUseCase = FinishGameUseCase()
) : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    fun onEvent(event : GameEvent){
        when(event){
            is GameEvent.OnGetCurrentGame -> onGetCurrentGame()
            is GameEvent.OnOpenCreateDialogSheet -> onOpenCreateDialogSheet()
            is GameEvent.OnCloseCreateDialogSheet -> _state.update { it.copy(showCreateGameSheet = false, players = emptyList(), loadingPlayers = false) }
            is GameEvent.OnValidateCreateGameSheet -> createGame(event.players)
            is GameEvent.OnNewRoundButtonPressed -> Unit
            is GameEvent.OnFinishGame -> onFinishGame(event.gameId)
        }
    }

    private fun createGame(players : List<PlayerModel>){
        viewModelScope.launch {
            when(val result = createGameUseCase(players)){
                is Resource.Success -> {
                    _state.update { it.copy(showCreateGameSheet = false, currentGame = result.data) }
                }
                is Resource.Error -> {
                }
            }
        }
    }

    private fun onGetCurrentGame(){
        _state.update { it.copy(loadingGame = true) }
        viewModelScope.launch {
            when(val result = getCurrentGameUseCase()){
                is Resource.Success -> {
                    _state.update { it.copy(currentGame = result.data, loadingGame = false) }
                }
                is Resource.Error -> {
                    _state.update { it.copy(loadingGame = false) }
                }
            }
        }
    }

    private fun onFinishGame(gameId : Long){
        viewModelScope.launch {
            when(val result = finishGameUseCase(gameId)){
                is Resource.Success -> {

                    _state.update { it.copy(currentGame = null) }
                }
                is Resource.Error -> {


                }
            }
        }
    }

    private fun onOpenCreateDialogSheet(){
        _state.update { it.copy(showCreateGameSheet = true, loadingPlayers = true) }

        viewModelScope.launch {
            when(val result = getPlayersUseCase()){
                is Resource.Success -> {
                    _state.update { it.copy(players = result.data, loadingPlayers = false) }
                }
                is Resource.Error -> {
                    _state.update { it.copy(loadingPlayers = false) }
                }
            }
        }
    }
}