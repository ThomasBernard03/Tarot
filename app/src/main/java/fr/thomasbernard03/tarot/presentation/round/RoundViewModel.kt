package fr.thomasbernard03.tarot.presentation.round

import androidx.lifecycle.ViewModel
import fr.thomasbernard03.tarot.presentation.history.HistoryEvent
import fr.thomasbernard03.tarot.presentation.history.HistoryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoundViewModel : ViewModel() {

    private val _state = MutableStateFlow(RoundState())
    val state: StateFlow<RoundState> = _state.asStateFlow()

    fun onEvent(event : RoundEvent){
        when(event){
            is RoundEvent.OnGoBack -> { }
        }
    }
}