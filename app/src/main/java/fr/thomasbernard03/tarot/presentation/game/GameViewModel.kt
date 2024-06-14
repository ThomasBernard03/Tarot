package fr.thomasbernard03.tarot.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.Player
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.usecases.CreateGameUseCase
import fr.thomasbernard03.tarot.domain.usecases.GetCurrentGameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    private val createGameUseCase: CreateGameUseCase = CreateGameUseCase(),
    private val getCurrentGameUseCase: GetCurrentGameUseCase = GetCurrentGameUseCase()
) : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    fun onEvent(event : GameEvent){
        when(event){
            is GameEvent.OnGetCurrentGame -> onGetCurrentGame()
            is GameEvent.OnOpenCreateDialogSheet -> _state.update { it.copy(showCreateGameSheet = true) }
            is GameEvent.OnCloseCreateDialogSheet -> _state.update { it.copy(showCreateGameSheet = false) }
            is GameEvent.OnValidateCreateGameSheet -> createGame(event.players)
        }
    }

    private fun createGame(players : List<CreatePlayerModel>){
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
        viewModelScope.launch {
            when(val result = getCurrentGameUseCase()){
                is Resource.Success -> {
                    _state.update { it.copy(currentGame = result.data) }
                }
                is Resource.Error -> {

                }
            }
        }
    }
}