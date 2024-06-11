package fr.thomasbernard03.tarot.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.usecases.GetGameHistoryUseCase
import fr.thomasbernard03.tarot.presentation.game.GameEvent
import fr.thomasbernard03.tarot.presentation.game.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val getGameHistoryUseCase: GetGameHistoryUseCase = GetGameHistoryUseCase()
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    fun onEvent(event : HistoryEvent){
        when(event){
            is HistoryEvent.OnGetGames -> onGetGames()
        }
    }

    private fun onGetGames(){
        viewModelScope.launch {
            _state.update { it.copy(loading = true)}
            when(val result = getGameHistoryUseCase()) {
                is Resource.Success -> {
                    _state.update { it.copy(games = result.data, loading = false) }
                }

                is Resource.Error -> {
                    _state.update { it.copy(loading = false) }
                }
            }
        }
    }
}