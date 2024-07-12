package domain.models.errors.player

sealed class CreatePlayerError {
    data object PlayerAlreadyExists : CreatePlayerError()
    data object UnknownError : CreatePlayerError()
}