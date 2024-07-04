package fr.thomasbernard03.tarot.domain.models.errors.player

sealed class EditPlayerError {
    data object PlayerNotFound : EditPlayerError()
    data object NameAlreadyTaken : EditPlayerError()
    data object UnknownError : EditPlayerError()
}