package fr.thomasbernard03.tarot.presentation.player.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.usecases.player.GetPlayerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val getPlayerUseCase: GetPlayerUseCase = GetPlayerUseCase()
) : ViewModel() {

    private val _state = MutableStateFlow(PlayerState())
    val state: StateFlow<PlayerState> = _state.asStateFlow()

    fun onEvent(event : PlayerEvent){
        when(event){
            is PlayerEvent.OnLoadPlayer -> onLoadPlayer(event.id)
            is PlayerEvent.OnGoBack -> Unit
            is PlayerEvent.OnPlayerColorChanged -> _state.update { it.copy(playerColor = event.color)}
            is PlayerEvent.OnPlayerNameChanged -> _state.update { it.copy(playerName = event.name)}
        }
    }

    private fun onLoadPlayer(id : Long){
        _state.update { it.copy(loadingPlayer = true) }
        viewModelScope.launch {
            when(val result = getPlayerUseCase(id)){
                is Resource.Success -> {
                    _state.update { it.copy(player = result.data, loadingPlayer = false, playerName = result.data.name, playerColor = result.data.color) }
                }
                is Resource.Error -> {
                    _state.update { it.copy(loadingPlayer = false) }
                }
            }
        }
    }
}