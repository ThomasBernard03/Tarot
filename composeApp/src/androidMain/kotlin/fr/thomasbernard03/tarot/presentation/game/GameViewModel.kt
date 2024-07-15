package fr.thomasbernard03.tarot.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.models.PlayerModel
import domain.models.Resource
import domain.models.errors.DeleteRoundError
import domain.usecases.game.CreateGameUseCase
import domain.usecases.game.FinishGameUseCase
import domain.usecases.game.GetCurrentGameUseCase
import domain.usecases.player.GetPlayersUseCase
import domain.usecases.round.DeleteRoundUseCase
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.helpers.ResourcesHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    private val createGameUseCase: CreateGameUseCase,
    private val getCurrentGameUseCase: GetCurrentGameUseCase,
    private val getPlayersUseCase: GetPlayersUseCase,
    private val finishGameUseCase: FinishGameUseCase,
    private val deleteRoundUseCase: DeleteRoundUseCase,
    private val resourcesHelper: ResourcesHelper
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
            is GameEvent.OnDeleteRound -> onDeleteRound(event.roundId)
            is GameEvent.OnDismissMessage -> _state.update { it.copy(message = "") }
            is GameEvent.OnEditRound -> Unit
            is GameEvent.OnGoToRoundDetail -> Unit
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
            val result = getPlayersUseCase.execute()

            if (result.isSuccess)
                _state.update { it.copy(players = result.getOrNull()!!, loadingPlayers = false) }
            else
                _state.update { it.copy(loadingPlayers = false) }
        }
    }

    private fun onDeleteRound(roundId : Long){
        viewModelScope.launch {
            when(val result = deleteRoundUseCase(roundId)){
                is Resource.Success -> {
                    val rounds = _state.value.currentGame?.rounds?.toMutableList()
                    rounds?.removeAll { it.id == roundId }
                    _state.update { it.copy(currentGame = _state.value.currentGame?.copy(rounds = rounds!!)) }
                }
                is Resource.Error -> {
                    val messageId =
                        when(result.data){
                            is DeleteRoundError.RoundNotFound -> R.string.error_round_not_found
                            is DeleteRoundError.UnknownError -> R.string.error_unknown
                        }

                    _state.update { it.copy(message = resourcesHelper.getString(messageId)) }
                }
            }
        }
    }
}