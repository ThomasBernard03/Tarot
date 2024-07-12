package fr.thomasbernard03.tarot.domain.models.errors.player

sealed class GetPlayerError {
    data object PlayerNotFound : GetPlayerError()
    data object UnknownError : GetPlayerError()
}