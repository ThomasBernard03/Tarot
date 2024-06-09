package fr.thomasbernard03.tarot.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.thomasbernard03.tarot.presentation.theme.Blue
import fr.thomasbernard03.tarot.presentation.theme.Green
import fr.thomasbernard03.tarot.presentation.theme.Orange
import fr.thomasbernard03.tarot.presentation.theme.Purple
import fr.thomasbernard03.tarot.presentation.theme.Red

@Composable
fun PlayerIcon(
    name: String,
    color: Color,
) {
    Box(
        modifier = Modifier
            .background(color = color, shape = CircleShape)
            .size(44.dp)
    ) {
        Text(
            text = name.first().uppercase(),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PlayerIconPreview() = PreviewComponent {
    PlayerIcon(name = "Thomas", color = Red)
    PlayerIcon(name = "Marianne", color = Blue)
    PlayerIcon(name = "Baptiste", color = Orange)
    PlayerIcon(name = "Artus", color = Green)
    PlayerIcon(name = "Anatole", color = Purple)
}