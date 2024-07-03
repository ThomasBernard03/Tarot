package fr.thomasbernard03.tarot.presentation.history

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.commons.SmallPadding
import fr.thomasbernard03.tarot.commons.extensions.toColor
import fr.thomasbernard03.tarot.commons.extensions.toPrettyDate
import fr.thomasbernard03.tarot.commons.extensions.toPrettyTime
import fr.thomasbernard03.tarot.domain.models.GameModel
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.presentation.components.ActionButton
import fr.thomasbernard03.tarot.presentation.components.Loader
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon
import fr.thomasbernard03.tarot.presentation.components.PreviewScreen
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun HistoryScreen(state : HistoryState, onEvent : (HistoryEvent) -> Unit) {

    LaunchedEffect(Unit) {
        onEvent(HistoryEvent.OnGetGames)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var selectedGameId by remember { mutableStateOf<Long?>(null) }
    val gameSheetState = rememberModalBottomSheetState()
    val sheetScope = rememberCoroutineScope()

    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.cards))
    val preloaderProgress by animateLottieCompositionAsState(
        composition = preloaderLottieComposition,
    )

    if (selectedGameId != null){
        ModalBottomSheet(
            sheetState = gameSheetState,
            onDismissRequest = { selectedGameId = null },
        ) {
            Column(
                modifier = Modifier.padding(bottom = LargePadding)
            ) {
                ActionButton(
                    enabled = state.games.firstOrNull { it.id == selectedGameId }?.finishedAt != null,
                    modifier = Modifier.fillMaxWidth(),
                    title = R.string.resume_game,
                    icon = R.drawable.card,
                    onClick = {
                        sheetScope.launch {
                            onEvent(HistoryEvent.OnResumeGame(selectedGameId!!))
                            gameSheetState.hide()
                            selectedGameId = null
                        }
                    }
                )

                ActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = R.string.edit_round,
                    icon = R.drawable.edit,
                    onClick = {
                        sheetScope.launch {
                            gameSheetState.hide()
                            selectedGameId = null
                        }
                    }
                )

                ActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = R.string.delete_round,
                    icon = R.drawable.bin,
                    onClick = {
                        sheetScope.launch {
                            gameSheetState.hide()
                            selectedGameId = null
                        }
                    }
                )
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MediumTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.history_page_title),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            scrollBehavior = scrollBehavior
        )

        if (state.loading) {
            Loader(
                modifier = Modifier.fillMaxSize(),
                message = R.string.history_loading_message
            )
        }
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                contentPadding = PaddingValues(LargePadding),
                verticalArrangement = Arrangement.spacedBy(MediumPadding)
            ) {
                items(state.games, key = { it.id }) { game ->

                    var expanded by rememberSaveable { mutableStateOf(false) }
                    val offsetX by animateDpAsState(targetValue = if (expanded) 0.dp else 24.dp)
                    val offsetY by animateDpAsState(targetValue = if (expanded) 48.dp else 0.dp)


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
                                    onLongClick = { selectedGameId = game.id }
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
                                        game.players.forEach { player ->
                                            PlayerIcon(
                                                name = player.name,
                                                color = player.color.toColor(),
                                            )
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
        }
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HistoryScreenLoadingPreview() = PreviewScreen {
    val state = HistoryState(loading = true)
    HistoryScreen(state = state, onEvent = {})
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HistoryScreenPreview() = PreviewScreen {
    val state = HistoryState(
        loading = false,
        games = listOf(
            GameModel(
                id = 1,
                startedAt = Date(),
                rounds = listOf(),
                players = listOf(
                    PlayerModel(id = 1, name = "Thomas", color = PlayerColor.GREEN),
                    PlayerModel(id = 2, name = "Marianne", color = PlayerColor.RED),
                    PlayerModel(id = 4, name = "Thibaut", color = PlayerColor.BLUE),
                )
            ),
            GameModel(
                id = 2,
                startedAt = Date(),
                rounds = listOf(),

                players = listOf(
                    PlayerModel(id = 1, name = "Thomas", color = PlayerColor.GREEN),
                    PlayerModel(id = 2, name = "Marianne", color = PlayerColor.RED),
                    PlayerModel(id = 4, name = "Thibaut", color = PlayerColor.BLUE),
                )
            ),
            GameModel(
                id = 3,
                startedAt = Date(),
                rounds = listOf(),
                players = listOf(
                    PlayerModel(id = 1, name = "Thomas", color = PlayerColor.GREEN),
                    PlayerModel(id = 2, name = "Marianne", color = PlayerColor.RED),
                    PlayerModel(id = 4, name = "Thibaut", color = PlayerColor.BLUE),
                )
            ),
        )
    )
    HistoryScreen(state = state, onEvent = {})
}