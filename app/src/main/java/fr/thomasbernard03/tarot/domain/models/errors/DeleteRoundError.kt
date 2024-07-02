package fr.thomasbernard03.tarot.domain.models.errors

sealed class DeleteRoundError {
    data class RoundNotFound(val roundId: Long) : DeleteRoundError()
    data object UnkownError : DeleteRoundError()
}