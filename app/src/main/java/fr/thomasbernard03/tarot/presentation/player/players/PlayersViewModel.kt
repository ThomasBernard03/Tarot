package fr.thomasbernard03.tarot.presentation.player.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.usecases.GetPlayersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayersViewModel(
    private val getPlayersUseCase: GetPlayersUseCase = GetPlayersUseCase()
) : ViewModel() {
    private val _state = MutableStateFlow(PlayersState())
    val state: StateFlow<PlayersState> = _state.asStateFlow()

    fun onEvent(event : PlayersEvent){
        when(event){
            is PlayersEvent.OnLoadPlayers -> onLoadPlayers()
            is PlayersEvent.OnPlayerSelected -> {

            }
        }
    }

    private fun onLoadPlayers() {
        viewModelScope.launch {
            when(val result = getPlayersUseCase()){
                is Resource.Success -> {
                    _state.update { it.copy(players = result.data) }
                }
                is Resource.Error -> {

                }
            }
        }
    }
}