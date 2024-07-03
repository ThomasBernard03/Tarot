package fr.thomasbernard03.tarot.presentation.history

sealed class HistoryEvent {
    data object OnGetGames : HistoryEvent()

    data class OnResumeGame(val gameId : Long) : HistoryEvent()
    data class OnDeleteGame(val gameId : Long) : HistoryEvent()
}