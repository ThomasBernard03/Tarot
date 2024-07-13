package fr.thomasbernard03.tarot.commons.extensions

import androidx.compose.ui.graphics.Color
import domain.models.PlayerColor
import fr.thomasbernard03.tarot.presentation.theme.Blue
import fr.thomasbernard03.tarot.presentation.theme.Brown
import fr.thomasbernard03.tarot.presentation.theme.Green
import fr.thomasbernard03.tarot.presentation.theme.Orange
import fr.thomasbernard03.tarot.presentation.theme.Pink
import fr.thomasbernard03.tarot.presentation.theme.Purple
import fr.thomasbernard03.tarot.presentation.theme.Red
import fr.thomasbernard03.tarot.presentation.theme.Yellow

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