package fr.thomasbernard03.tarot.commons.extensions

import androidx.compose.ui.graphics.Color
import domain.models.PlayerColor
import fr.thomasbernard03.tarot.presentation.theme.*

fun PlayerColor.toColor() : Color {
    return when(this) {
        PlayerColor.RED -> Red
        PlayerColor.BLUE -> Blue
        PlayerColor.GREEN -> Green
        PlayerColor.ORANGE -> Orange
        PlayerColor.PURPLE -> Purple
        PlayerColor.YELLOW -> Yellow
        PlayerColor.PINK -> Pink
        PlayerColor.BROWN -> Brown
    }
}