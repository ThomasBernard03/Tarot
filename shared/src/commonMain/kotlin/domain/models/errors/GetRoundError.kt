package domain.models.errors

sealed class GetRoundError {
    data class RoundNotFound(val roundId : Long) : GetRoundError()
    data class GameNotFound(val gameId : Long) : GetRoundError()
    data object UnknownError : GetRoundError()
}