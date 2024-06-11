package fr.thomasbernard03.tarot.domain.models.errors

sealed class GetGameError {
    data object UnknownError : GetGameError()
}