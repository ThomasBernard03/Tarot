package fr.thomasbernard03.tarot.presentation.history

import fr.thomasbernard03.tarot.domain.models.Game

data class HistoryState(
    val loading : Boolean = false,
    val games : List<Game> = emptyList()
)