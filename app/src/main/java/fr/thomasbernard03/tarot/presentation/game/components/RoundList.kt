package fr.thomasbernard03.tarot.presentation.game.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.commons.SmallPadding
import fr.thomasbernard03.tarot.commons.calculatePartnerScore
import fr.thomasbernard03.tarot.commons.calculateTakerScore
import fr.thomasbernard03.tarot.commons.extensions.toColor
import fr.thomasbernard03.tarot.domain.models.RoundModel
import fr.thomasbernard03.tarot.presentation.components.OudlerIndicator
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon
import fr.thomasbernard03.tarot.presentation.theme.Green
import fr.thomasbernard03.tarot.presentation.theme.Red

fun LazyListScope.roundList(
    rounds: List<RoundModel>,
){
    items(rounds, key = { it.id }){ round ->
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            shape = RectangleShape,
            contentPadding = PaddingValues(horizontal = LargePadding, vertical = MediumPadding)
        ) {
            Row(
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

                    if (round.calledPlayer != null && round.calledPlayer.id != round.taker.id){
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
                                name = round.calledPlayer.name,
                                color = round.calledPlayer.color.toColor()
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
                }

                Column (
                    horizontalAlignment = Alignment.End
                ){
                    // TODO Change this with game players count
                    val takerScore =
                        calculateTakerScore(round.points, round.bid, round.oudlers.size, 5, round.taker.id == round.calledPlayer?.id)

                    Text(
                        text = "$takerScore",
                        color = if (takerScore >= 0) Green else Red,
                        fontWeight =  FontWeight.Bold
                    )

                    if (round.calledPlayer != null && round.taker.id != round.calledPlayer.id){

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