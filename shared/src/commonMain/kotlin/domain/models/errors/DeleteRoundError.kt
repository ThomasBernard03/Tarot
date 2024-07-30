package domain.models.errors

sealed class DeleteRoundError {
    data class RoundNotFound(val roundId: Long) : DeleteRoundError()
    data object UnknownError : DeleteRoundError()
}