package fr.thomasbernard03.tarot.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MediumPadding


@Composable
fun PlayerChip(
    name : String,
    color : Color,
    modifier : Modifier = Modifier,
    selected : Boolean = false,
    enabled : Boolean = true,
    onClick : () -> Unit = {},
) {
    PlayerButton(
        modifier = modifier,
        name = name,
        color = color,
        onClick = if(enabled) onClick else null,
        backgroundColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
    )
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PlayerButtonPreview() = PreviewComponent {
    PlayerChip(
        modifier = Modifier.padding(horizontal = LargePadding),
        name = "Thomas",
        color = Color.Red,
        selected = true
    )
    PlayerChip(
        modifier = Modifier.padding(horizontal = LargePadding),
        name = "Marianne",
        color = Color.Blue,
        selected = false
    )
}