package fr.thomasbernard03.tarot.domain.models.errors

sealed class EditRoundError {
    data class RoundNotFound(val roundId: Long) : EditRoundError()
    data object UnknownError : EditRoundError()
}