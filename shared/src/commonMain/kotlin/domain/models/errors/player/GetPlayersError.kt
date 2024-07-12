package domain.models.errors.player

sealed class GetPlayersError {
    data object UnknownError : GetPlayersError()
}