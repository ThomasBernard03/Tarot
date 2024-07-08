package fr.thomasbernard03.tarot.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.thomasbernard03.tarot.commons.helpers.NavigationHelper
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.Screen
import fr.thomasbernard03.tarot.domain.usecases.game.DeleteGameUseCase
import fr.thomasbernard03.tarot.domain.usecases.game.GetGameHistoryUseCase
import fr.thomasbernard03.tarot.domain.usecases.game.ResumeGameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.get

class HistoryViewModel(
    private val getGameHistoryUseCase: GetGameHistoryUseCase = GetGameHistoryUseCase(),
    private val resumeGameUseCase: ResumeGameUseCase = ResumeGameUseCase(),
    private val deleteGameUseCase: DeleteGameUseCase = DeleteGameUseCase(),
    private val navigationHelper: NavigationHelper = get(NavigationHelper::class.java)
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    fun onEvent(event : HistoryEvent){
        when(event){
            is HistoryEvent.OnGetGames -> onGetGames()
            is HistoryEvent.OnResumeGame -> onResumeGame(event.gameId)
            is HistoryEvent.OnDeleteGame -> onDeleteGame(event.gameId)
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

    private fun onResumeGame(id : Long){
        viewModelScope.launch {
            when(val result = resumeGameUseCase(id)){
                is Resource.Success -> {
                    navigationHelper.navigateTo(Screen.Game, popupTo = Screen.History)
                }
                is Resource.Error -> {

                }
            }
        }
    }

    private fun onDeleteGame(id : Long){
        viewModelScope.launch {
            when(val result = deleteGameUseCase(id)){
                is Resource.Success -> {
                    val games = _state.value.games.toMutableList()
                    games.removeIf { it.id == id }
                    _state.update { it.copy(games = games) }
                }
                is Resource.Error -> {

                }
            }
        }
    }
}