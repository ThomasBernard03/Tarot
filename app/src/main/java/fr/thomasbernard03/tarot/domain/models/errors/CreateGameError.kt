package fr.thomasbernard03.tarot.domain.models.errors

sealed class CreateGameError {
    data object UnknownError : CreateGameError()
}