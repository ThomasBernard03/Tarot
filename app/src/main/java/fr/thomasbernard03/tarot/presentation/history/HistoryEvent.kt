package fr.thomasbernard03.tarot.presentation.history

sealed class HistoryEvent {
    data object OnGetGames : HistoryEvent()
}