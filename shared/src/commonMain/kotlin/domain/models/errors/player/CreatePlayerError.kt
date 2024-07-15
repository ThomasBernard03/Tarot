package domain.models.errors.player

sealed class CreatePlayerError : Exception() {
    data object PlayerAlreadyExists : CreatePlayerError()
    data object UnknownError : CreatePlayerError()
}