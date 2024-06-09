package fr.thomasbernard03.tarot.commons

import androidx.compose.ui.graphics.Color
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.presentation.theme.Blue
import fr.thomasbernard03.tarot.presentation.theme.Green
import fr.thomasbernard03.tarot.presentation.theme.Orange
import fr.thomasbernard03.tarot.presentation.theme.Purple
import fr.thomasbernard03.tarot.presentation.theme.Red

fun PlayerColor.toColor() : Color {
    return when(this) {
        PlayerColor.RED -> Red
        PlayerColor.BLUE -> Blue
        PlayerColor.GREEN -> Green
        PlayerColor.ORANGE -> Orange
        PlayerColor.PURPLE -> Purple
    }
}