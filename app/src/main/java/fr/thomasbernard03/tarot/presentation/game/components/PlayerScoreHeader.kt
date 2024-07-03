package fr.thomasbernard03.tarot.presentation.game.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.commons.SmallPadding
import fr.thomasbernard03.tarot.commons.calculateDefenderScore
import fr.thomasbernard03.tarot.commons.calculatePartnerScore
import fr.thomasbernard03.tarot.commons.calculateTakerScore
import fr.thomasbernard03.tarot.commons.extensions.calculateScore
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
                .padding(horizontal = SmallPadding, vertical = LargePadding)
        ) {
            game.calculateScore().sortedByDescending { it.second }.forEach { playerWithScore ->
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box {
                        PlayerIcon(
                            name = playerWithScore.first.name,
                            color = playerWithScore.first.color.toColor()
                        )

                        Box(modifier = Modifier
                            .offset(x = 12.dp, y = 8.dp)
                            .zIndex(1f)
                            .align(Alignment.BottomEnd)
                            .background(MaterialTheme.colorScheme.onBackground, CircleShape)
                            .height(24.dp)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.background,
                                CircleShape
                            )
                            .padding(horizontal = MediumPadding, vertical = SmallPadding)
                        ){
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = playerWithScore.second.toString(),
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