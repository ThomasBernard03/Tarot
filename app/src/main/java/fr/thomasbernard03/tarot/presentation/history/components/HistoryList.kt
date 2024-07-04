package fr.thomasbernard03.tarot.presentation.history.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import fr.thomasbernard03.tarot.R
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
import fr.thomasbernard03.tarot.presentation.theme.Green
import fr.thomasbernard03.tarot.presentation.theme.Red
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.historyList(
    games : List<GameModel>,
    onGameLongPressed : (Long) -> Unit,
){
    items(games, key = { it.id }) { game ->

        var expanded by rememberSaveable { mutableStateOf(false) }

        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
        ) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .combinedClickable(
                        onClick = { expanded = !expanded },
                        onLongClick = { onGameLongPressed(game.id) }
                    )
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(LargePadding)
                ) {
                    if (!expanded){
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(SmallPadding)
                        ) {
                            game.players.forEach { player ->
                                PlayerIcon(
                                    name = player.name,
                                    color = player.color.toColor(),
                                    modifier = Modifier.size(28.dp),
                                    style = LocalTextStyle.current.copy(fontSize = 14.sp)
                                )
                            }
                        }
                    }
                    else {
                        Column(verticalArrangement = Arrangement.spacedBy(SmallPadding)) {
                            game.calculateScore().forEach { (player, score) ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(LargePadding)
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(MediumPadding)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .background(player.color.toColor(), shape = CircleShape)
                                        )

                                        Text(text = player.name)
                                    }


                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = score.toString(),
                                        color = if (score >= 0) Green else Red,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    if (!expanded){
                        Text(
                            modifier = Modifier.padding(horizontal = MediumPadding),
                            text = "${game.rounds.size} tours"
                        )
                    }

                }

                Box(
                    modifier = Modifier
                        .width(70.dp)
                        .background(MaterialTheme.colorScheme.tertiary)
                        .fillMaxHeight()
                ) {
                    if (game.finishedAt != null){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = game.startedAt.day.toString(),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onTertiary
                                )

                                Text(
                                    text = String.format(Locale.getDefault(), "%tB", game.startedAt),
                                    color = MaterialTheme.colorScheme.onTertiary,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            if (expanded){
                                HorizontalDivider()
                            }

                            if(expanded) {
                                Column(
                                    modifier = Modifier
                                        .padding(vertical = LargePadding),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "${game.rounds.size}",
                                        color = MaterialTheme.colorScheme.onTertiary,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = "Rounds",
                                        color = MaterialTheme.colorScheme.onTertiary,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    else {
                        val preloaderLottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cards))
                        val preloaderProgress by animateLottieCompositionAsState(
                            composition = preloaderLottieComposition,
                        )

                        LottieAnimation(
                            composition = preloaderLottieComposition,
                            progress = preloaderProgress,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .offset(y = 12.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}