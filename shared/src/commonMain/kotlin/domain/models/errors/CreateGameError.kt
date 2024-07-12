package fr.thomasbernard03.tarot.domain.models.errors

sealed class CreateGameError {
    data object GameAlreadyInProgress : CreateGameError()
    data object UnknownError : CreateGameError()
    data object InvalidNumberOfPlayers : CreateGameError()
}