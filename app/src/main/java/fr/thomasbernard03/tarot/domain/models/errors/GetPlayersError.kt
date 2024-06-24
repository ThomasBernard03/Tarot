package fr.thomasbernard03.tarot.domain.models.errors

sealed class GetPlayersError {
    data object UnknownError : GetPlayersError()
}