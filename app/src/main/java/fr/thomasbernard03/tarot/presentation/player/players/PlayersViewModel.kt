package fr.thomasbernard03.tarot.presentation.player.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.helpers.ResourcesHelper
import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.usecases.CreatePlayerUseCase
import fr.thomasbernard03.tarot.domain.usecases.GetPlayersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.get

class PlayersViewModel(
    private val getPlayersUseCase: GetPlayersUseCase = GetPlayersUseCase(),
    private val createPlayerUseCase: CreatePlayerUseCase = CreatePlayerUseCase(),
    private var resourcesHelper: ResourcesHelper = get(ResourcesHelper::class.java)
) : ViewModel() {
    private val _state = MutableStateFlow(PlayersState())
    val state: StateFlow<PlayersState> = _state.asStateFlow()

    fun onEvent(event : PlayersEvent){
        when(event){
            is PlayersEvent.OnLoadPlayers -> onLoadPlayers()
            is PlayersEvent.OnPlayerSelected -> {

            }

            is PlayersEvent.OnCreatePlayerDialogColorSelected -> _state.update { it.copy(createPlayerDialogColor = event.color) }
            is PlayersEvent.OnCreatePlayerDialogNameChanged -> onCreatePlayerDialogNameChanged(event.name)
            is PlayersEvent.OnDismissCreatePlayerDialog -> _state.update { it.copy(showCreatePlayerDialog = false, createPlayerDialogMessage = "", createPlayerDialogName = "", createPlayerDialogColor = null) }
            is PlayersEvent.OnShowCreatePlayerDialog -> _state.update { it.copy(showCreatePlayerDialog = true) }
            is PlayersEvent.OnCreatePlayerDialogValidated -> onCreatePlayer(event.name, event.color)
        }
    }

    private fun onLoadPlayers() {
        _state.update { it.copy(loadingPlayers = true) }
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
}