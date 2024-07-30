package domain.models.errors.player

sealed class DeletePlayerError {
    data class PlayerNotFound(val id: Long) : DeletePlayerError()
    data object PlayerHasGames : DeletePlayerError()
    data object UnknownError : DeletePlayerError()

}