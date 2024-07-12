package domain.models.errors

sealed class CreateRoundError {
    data object GameNotFound : CreateRoundError()
    data object UnkownError : CreateRoundError()
}