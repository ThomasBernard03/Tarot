package fr.thomasbernard03.tarot.presentation.round

sealed class RoundEvent {
    data object OnGoBack : RoundEvent()
}