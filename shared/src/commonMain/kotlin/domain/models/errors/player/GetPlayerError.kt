package domain.models.errors.player

sealed class GetPlayerError {
    data object PlayerNotFound : GetPlayerError()
    data object UnknownError : GetPlayerError()
}