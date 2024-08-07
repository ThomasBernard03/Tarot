package domain.models

sealed class Screen(val route : String) {
    data object Game : Screen(route = "game")
    data object History : Screen(route = "history")

    data class Player(val playerId : Long) : Screen(route = "player/$playerId") {
        companion object {
            const val PATH = "player/{playerId}"
        }
    }
    data class Round(val gameId : Long) : Screen(route = "round/$gameId") {
        companion object {
            const val PATH = "round/{gameId}?roundId={roundId}&editable={editable}"
        }
    }
}