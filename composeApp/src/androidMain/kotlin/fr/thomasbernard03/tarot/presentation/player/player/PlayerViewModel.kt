package fr.thomasbernard03.tarot.presentation.player.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.models.PlayerColor
import domain.models.Resource
import domain.models.errors.player.DeletePlayerError
import domain.models.errors.player.EditPlayerError
import domain.usecases.player.DeletePlayerUseCase
import domain.usecases.player.EditPlayerUseCase
import domain.usecases.player.GetPlayerUseCase
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.helpers.NavigationHelper
import fr.thomasbernard03.tarot.commons.helpers.ResourcesHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val getPlayerUseCase: GetPlayerUseCase,
    private val deletePlayerUseCase: DeletePlayerUseCase,
    private val editPlayerUseCase: EditPlayerUseCase,
    private val resourcesHelper: ResourcesHelper,
    private val navigationHelper: NavigationHelper
) : ViewModel() {

    private val _state = MutableStateFlow(PlayerState())
    val state: StateFlow<PlayerState> = _state.asStateFlow()

    fun onEvent(event : PlayerEvent){
        when(event){
            is PlayerEvent.OnLoadPlayer -> onLoadPlayer(event.id)
            is PlayerEvent.OnGoBack -> Unit
            is PlayerEvent.OnDeletePlayer -> onDeletePlayer(event.id)
            is PlayerEvent.OnEditPlayer -> onEditPlayer(event.id, event.name, event.color)
            is PlayerEvent.OnDismissMessage -> _state.update { it.copy(message = "") }  
        }
    }

    private fun onLoadPlayer(id : Long){
        _state.update { it.copy(loadingPlayer = true) }
        viewModelScope.launch {
            when(val result = getPlayerUseCase(id)){
                is Resource.Success -> {
                    _state.update { it.copy(player = result.data, loadingPlayer = false) }
                }
                is Resource.Error -> {
                    _state.update { it.copy(loadingPlayer = false) }
                }
            }
        }
    }


    private fun onDeletePlayer(id : Long){
        viewModelScope.launch {
            when(val result = deletePlayerUseCase(id)){
                is Resource.Success -> {
                    navigationHelper.goBack()
                }
                is Resource.Error -> {
                    val messageId = when(result.data){
                        is DeletePlayerError.PlayerHasGames -> R.string.error_delete_player_has_game
                        is DeletePlayerError.PlayerNotFound -> R.string.error_player_not_found
                        is DeletePlayerError.UnknownError -> R.string.error_unknown
                    }

                    _state.update { it.copy(message = resourcesHelper.getString(messageId)) }
                }
            }
        }
    }

    private fun onEditPlayer(id : Long, name : String, color : PlayerColor){
        viewModelScope.launch {
            when(val result =  editPlayerUseCase(id, name, color)){
                is Resource.Success -> {
                    _state.update { it.copy(player = result.data) }
                }
                is Resource.Error -> {
                    val messageId = when(result.data){
                        EditPlayerError.NameAlreadyTaken -> R.string.error_player_name_already_used
                        EditPlayerError.PlayerNotFound -> R.string.error_player_not_found
                        EditPlayerError.UnknownError -> R.string.error_unknown
                    }

                    _state.update { it.copy(message = resourcesHelper.getString(messageId)) }
                }
            }
        }
    }
}