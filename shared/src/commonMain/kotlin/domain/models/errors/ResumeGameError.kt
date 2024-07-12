package fr.thomasbernard03.tarot.domain.models.errors

sealed class ResumeGameError {
    data class GameNotFound(val gameId: Long) : ResumeGameError()
    data object UnknownError : ResumeGameError()
}