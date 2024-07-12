package fr.thomasbernard03.tarot.presentation.history

import domain.models.GameModel

data class HistoryState(
    val loading : Boolean = false,
    val games : List<GameModel> = emptyList()
)