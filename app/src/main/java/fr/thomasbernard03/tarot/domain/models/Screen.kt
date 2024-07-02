package fr.thomasbernard03.tarot.domain.models

sealed class Screen(val route : String) {
    data object Game : Screen(route = "game")
    data class Round(val gameId : Long) : Screen(route = "round/$gameId") {
        companion object {
            const val PATH = "round/{gameId}?roundId={roundId}"
        }
    }
}