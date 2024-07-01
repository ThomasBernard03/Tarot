package fr.thomasbernard03.tarot.presentation.game.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.commons.SmallPadding
import fr.thomasbernard03.tarot.commons.extensions.toColor
import fr.thomasbernard03.tarot.domain.models.GameModel
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.playersScoreHeader(
    game : GameModel
) {
    stickyHeader {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = SmallPadding, vertical = MediumPadding)
        ) {
            game.players.forEach { player ->
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box {
                        PlayerIcon(
                            name = player.name,
                            color = player.color.toColor()
                        )

                        Box(modifier = Modifier
                            .offset(x = 8.dp)
                            .zIndex(1f)
                            .align(Alignment.BottomEnd)
                            .background(MaterialTheme.colorScheme.onBackground, CircleShape)
                            .size(24.dp)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.background,
                                CircleShape
                            )
                        ){
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = game.rounds.count { it.taker.id == player.id }.toString(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.background,
                            )
                        }
                    }
                }
            }
        }
    }
}