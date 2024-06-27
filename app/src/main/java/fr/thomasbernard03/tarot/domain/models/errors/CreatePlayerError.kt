package fr.thomasbernard03.tarot.domain.models.errors

sealed class CreatePlayerError {
    data object PlayerAlreadyExists : CreatePlayerError()
    data object UnknownError : CreatePlayerError()
}