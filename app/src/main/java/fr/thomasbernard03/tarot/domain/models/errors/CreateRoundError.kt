package fr.thomasbernard03.tarot.domain.models.errors

sealed class CreateRoundError {
    data object GameNotFound : CreateRoundError()
    data object UnkownError : CreateRoundError()
}