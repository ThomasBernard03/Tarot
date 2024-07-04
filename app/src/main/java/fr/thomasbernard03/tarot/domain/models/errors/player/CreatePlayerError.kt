package fr.thomasbernard03.tarot.domain.models.errors.player

sealed class CreatePlayerError {
    data object PlayerAlreadyExists : CreatePlayerError()
    data object UnknownError : CreatePlayerError()
}