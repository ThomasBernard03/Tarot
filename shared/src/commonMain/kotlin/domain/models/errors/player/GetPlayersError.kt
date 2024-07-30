package domain.models.errors.player

sealed class GetPlayersError : Exception() {
    data object UnknownError : GetPlayersError()
}