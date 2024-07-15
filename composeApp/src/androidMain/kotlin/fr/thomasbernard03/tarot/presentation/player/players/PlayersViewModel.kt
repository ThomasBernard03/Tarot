package fr.thomasbernard03.tarot.presentation.player.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.models.CreatePlayerModel
import domain.models.PlayerColor
import domain.models.Resource
import domain.models.errors.player.DeletePlayerError
import domain.usecases.player.CreatePlayerUseCase
import domain.usecases.player.DeletePlayerUseCase
import domain.usecases.player.GetPlayersUseCase
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.helpers.ResourcesHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayersViewModel(
    private val getPlayersUseCase: GetPlayersUseCase,
    private val createPlayerUseCase: CreatePlayerUseCase,
    private val deletePlayerUseCase: DeletePlayerUseCase,
    private var resourcesHelper: ResourcesHelper
) : ViewModel() {
    private val _state = MutableStateFlow(PlayersState())
    val state: StateFlow<PlayersState> = _state.asStateFlow()

    fun onEvent(event : PlayersEvent){
        when(event){
            is PlayersEvent.OnLoadPlayers -> onLoadPlayers()
            is PlayersEvent.OnCreatePlayerDialogColorSelected -> _state.update { it.copy(createPlayerDialogColor = event.color) }
            is PlayersEvent.OnCreatePlayerDialogNameChanged -> onCreatePlayerDialogNameChanged(event.name)
            is PlayersEvent.OnDismissCreatePlayerDialog -> _state.update { it.copy(showCreatePlayerDialog = false, createPlayerDialogMessage = "", createPlayerDialogName = "", createPlayerDialogColor = null) }
            is PlayersEvent.OnShowCreatePlayerDialog -> _state.update { it.copy(showCreatePlayerDialog = true) }
            is PlayersEvent.OnCreatePlayerDialogValidated -> onCreatePlayer(event.name, event.color)
            is PlayersEvent.OnDeletePlayer -> onDeletePlayer(event.playerId)
            is PlayersEvent.OnDismissMessage -> _state.update { it.copy(message = "") }
            is PlayersEvent.OnEditPlayer -> Unit
            is PlayersEvent.OnPlayerSelected -> Unit
        }
    }

    private fun onLoadPlayers() {
        _state.update { it.copy(loadingPlayers = true) }
        viewModelScope.launch {

            val result = getPlayersUseCase.execute()
            if (result.isSuccess)
                _state.update { it.copy(players = result.getOrNull()!!, loadingPlayers = false) }
            else
                _state.update { it.copy(loadingPlayers = false) }
        }
    }

    private fun onCreatePlayer(name: String, color: PlayerColor) {
        _state.update { it.copy(createPlayerDialogLoading = true) }
        viewModelScope.launch {
            val createPlayerModel = CreatePlayerModel(name, color)
            when(val result = createPlayerUseCase(createPlayerModel)){
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            players = it.players + result.data,
                            createPlayerDialogLoading = false,
                            showCreatePlayerDialog = false,
                            createPlayerDialogMessage = "",
                            createPlayerDialogName = "",
                            createPlayerDialogColor = null
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update { it.copy(createPlayerDialogLoading = false) }
                }
            }
        }
    }

    private fun onCreatePlayerDialogNameChanged(name : String){
        _state.update { it.copy(createPlayerDialogMessage = "") }

        if (_state.value.players.any { it.name == name })
            _state.update { it.copy(createPlayerDialogMessage = resourcesHelper.getString(R.string.create_player_dialog_player_name_already_used_error)) }

        _state.update { it.copy(createPlayerDialogName = name) }
    }

    private fun onDeletePlayer(playerId: Long) {
        viewModelScope.launch {
            when(val result = deletePlayerUseCase(playerId)){
                is Resource.Success -> {
                    val players = _state.value.players.filter { it.id != playerId }
                    _state.update { it.copy(players = players) }
                }
                is Resource.Error -> {
                    val messageId =
                        when(result.data){
                            is DeletePlayerError.PlayerHasGames -> R.string.error_delete_player_has_game
                            is DeletePlayerError.PlayerNotFound -> R.string.error_player_not_found
                            is DeletePlayerError.UnknownError -> R.string.error_unknown
                        }

                    _state.update { it.copy(message = resourcesHelper.getString(messageId)) }
                }
            }
        }
    }
}