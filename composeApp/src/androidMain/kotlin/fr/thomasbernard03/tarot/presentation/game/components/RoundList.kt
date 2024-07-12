package fr.thomasbernard03.tarot.presentation.game.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import commons.calculatePartnerScore
import commons.calculateTakerScore
import domain.models.GameModel
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.commons.SmallPadding
import fr.thomasbernard03.tarot.commons.extensions.toColor
import fr.thomasbernard03.tarot.commons.extensions.toText
import fr.thomasbernard03.tarot.presentation.components.OudlerIndicator
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon
import fr.thomasbernard03.tarot.presentation.theme.Green
import fr.thomasbernard03.tarot.presentation.theme.Red

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.roundList(
    game : GameModel,
    onRoundPressed : (Long) -> Unit,
    onRoundLongPressed : (Long) -> Unit
){
    items(game.rounds, key = { it.id }){ round ->
        Row(
            modifier = Modifier.combinedClickable(
                onClick = {
                    onRoundPressed(round.id)
                },
                onLongClick = {
                    onRoundLongPressed(round.id)
                }
            )
        ) {
            Row(
                modifier = Modifier.padding(horizontal = LargePadding, vertical = MediumPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MediumPadding)
            ) {
                // Taker and called player picture
                Box(
                    modifier = Modifier.padding(end = MediumPadding)
                ) {
                    PlayerIcon(
                        name = round.taker.name,
                        color = round.taker.color.toColor()
                    )

                    if (round.calledPlayer != null && round.calledPlayer!!.id != round.taker.id){
                        Box(modifier = Modifier
                            .offset(x = 8.dp)
                            .zIndex(1f)
                            .align(Alignment.BottomEnd)
                        ){
                            PlayerIcon(
                                modifier = Modifier
                                    .size(24.dp)
                                    .border(
                                        1.dp,
                                        MaterialTheme.colorScheme.background,
                                        CircleShape
                                    ),
                                style = LocalTextStyle.current.copy(fontSize = 10.sp),
                                name = round.calledPlayer!!.name,
                                color = round.calledPlayer!!.color.toColor()
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(SmallPadding)
                ) {
                    round.oudlers.forEach {
                        OudlerIndicator(oudler = it)
                    }

                    Text(
                        modifier = Modifier.padding(start = MediumPadding),
                        text = round.bid.toText()
                    )
                }

                Column (
                    horizontalAlignment = Alignment.End
                ){
                    val takerScore =
                        calculateTakerScore(
                            points = round.points,
                            bid = round.bid,
                            oudlers = round.oudlers.size,
                            calledHimSelf = round.calledPlayer == round.taker
                        )

                    Text(
                        text = "$takerScore",
                        color = if (takerScore >= 0) Green else Red,
                        fontWeight =  FontWeight.Bold
                    )

                    if (round.calledPlayer != null && round.taker.id != round.calledPlayer!!.id){

                        val calledPlayerScore = calculatePartnerScore(takerScore)

                        Text(
                            text = "$calledPlayerScore",
                            color = if (takerScore >= 0) Green else Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Icon(
                    painter = painterResource(id = R.drawable.chevron_right),
                    contentDescription = null
                )
            }
        }
    }
}