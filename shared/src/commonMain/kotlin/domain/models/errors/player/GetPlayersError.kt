package fr.thomasbernard03.tarot.domain.models.errors.player

sealed class GetPlayersError {
    data object UnknownError : GetPlayersError()
}