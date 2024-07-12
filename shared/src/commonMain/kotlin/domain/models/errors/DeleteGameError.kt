package fr.thomasbernard03.tarot.domain.models.errors

sealed class DeleteGameError {
    data object GameInProgressError : DeleteGameError()
    data object GameNotFound : DeleteGameError()
    data object UnknownError : DeleteGameError()
}