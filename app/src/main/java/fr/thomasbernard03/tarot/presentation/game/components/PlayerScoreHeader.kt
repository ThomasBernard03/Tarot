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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.commons.SmallPadding
import fr.thomasbernard03.tarot.commons.calculateDefenderScore
import fr.thomasbernard03.tarot.commons.calculatePartnerScore
import fr.thomasbernard03.tarot.commons.calculateTakerScore
import fr.thomasbernard03.tarot.commons.extensions.toColor
import fr.thomasbernard03.tarot.domain.models.GameModel
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.playersScoreHeader(
    game : GameModel
) {
    stickyHeader {

        val playersWithScore = game.players.map { player ->
            player to
                    game.rounds.sumOf {
                        val takerScore = calculateTakerScore(
                            points = it.points,
                            bid = it.bid,
                            oudlers = it.oudlers.size,
                        )

                        val partnerScore = calculatePartnerScore(takerScore)

                        if (it.taker == player)
                            takerScore + if(it.calledPlayer == it.taker) partnerScore else 0
                        else if (it.calledPlayer == player)
                            partnerScore
                        else {
                            val numberOfDefenders = game.players.size - if (it.calledPlayer == null || it.calledPlayer == it.taker) 1 else 2
                            val attackScore = takerScore + if (it.calledPlayer == null || it.calledPlayer == it.taker) 0 else partnerScore
                            calculateDefenderScore(attackScore, numberOfDefenders)
                        }
                    }
        }
            .sortedByDescending { it.second }

        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = SmallPadding, vertical = MediumPadding)
        ) {
            playersWithScore.forEach { playerWithScore ->
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