package fr.thomasbernard03.tarot.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MediumPadding

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerButton(
    modifier : Modifier = Modifier,
    name : String,
    color : Color,
    onClick :( () -> Unit)? = null,
    onLongClick : (() -> Unit)? = {},
    backgroundColor : Color = MaterialTheme.colorScheme.surface,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
        color = backgroundColor
    ){
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .then(
                    if (onClick != null)
                        Modifier.combinedClickable(
                            onClick = onClick,
                            onLongClick = onLongClick
                        )
                    else
                        Modifier
                ),
            verticalAlignment = Alignment.CenterVertically
        ){
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = LargePadding, vertical = MediumPadding),
                horizontalArrangement = Arrangement.spacedBy(MediumPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PlayerIcon(
                    modifier = Modifier.size(34.dp),
                    name = name,
                    color = color,
                    style = LocalTextStyle.current.copy(fontSize = 16.sp)
                )

                Text(text = name)
            }
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .background(color)
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PlayerButtonPreview() = PreviewComponent {
    PlayerButton(
        modifier = Modifier.padding(horizontal = LargePadding),
        name = "Thomas",
        color = Color.Red
    )
    PlayerButton(
        modifier = Modifier.padding(horizontal = LargePadding),
        name = "Marianne",
        color = Color.Blue
    )
}