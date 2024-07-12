package domain.models.errors

sealed class FinishGameError {
    data object GameNotFound : FinishGameError()
    data object GameAlreadyFinished : FinishGameError()
    data object UnknownError : FinishGameError()
}