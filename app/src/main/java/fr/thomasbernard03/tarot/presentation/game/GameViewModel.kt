package fr.thomasbernard03.tarot.presentation.game

import androidx.lifecycle.ViewModel
import fr.thomasbernard03.tarot.domain.models.Player
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    fun onEvent(event : GameEvent){
        when(event){
            is GameEvent.OnOpenCreateDialogSheet -> _state.update { it.copy(showCreateGameSheet = true) }
            is GameEvent.OnCloseCreateDialogSheet -> _state.update { it.copy(showCreateGameSheet = false) }
            is GameEvent.OnCreateNewPlayer -> onCreateNewPlayer()
            is GameEvent.OnPlayersChanged -> _state.update { it.copy(players = event.players) }
            is GameEvent.OnValidateCreateGameSheet -> _state.update { it.copy(showCreateGameSheet = false) }
        }
    }


    private fun onCreateNewPlayer(){
        val number = (_state.value.players.size + 1).toLong()
        val randomColor = PlayerColor.entries.toTypedArray().random()
        _state.update { it.copy(players = it.players + Player(id = number, name = "Joueur $number", color = randomColor)) }
    }
}