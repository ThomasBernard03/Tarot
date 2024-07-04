package fr.thomasbernard03.tarot.presentation.player.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.commons.extensions.toColor
import fr.thomasbernard03.tarot.domain.models.PlayerColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlayerColorPicker(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: PlayerColor?,
    onColorSelected: (PlayerColor?) -> Unit,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MediumPadding),
        verticalArrangement = Arrangement.spacedBy(MediumPadding)
    ) {
        PlayerColor.entries.forEach {
            Box {
                Button(
                    enabled = enabled,
                    contentPadding = PaddingValues(0.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = it.toColor(),
                        contentColor = MaterialTheme.colorScheme.background,
                        disabledContainerColor = it.toColor(),
                    ),
                    onClick = {
                        if (color == it) onColorSelected(null)
                        else onColorSelected(it)
                    },
                    border = if (color == it) BorderStroke(4.dp, MaterialTheme.colorScheme.primary) else null,
                    modifier = Modifier.size(48.dp)
                ) { }

                if (color == it && enabled) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.check),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier
                                .size(16.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}