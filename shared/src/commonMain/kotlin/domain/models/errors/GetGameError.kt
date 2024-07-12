package domain.models.errors

sealed class GetGameError {
    data object UnknownError : GetGameError()
    data object GameNotFound : GetGameError()
}