package fr.thomasbernard03.tarot.presentation.history

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.extensions.toColor
import fr.thomasbernard03.tarot.commons.extensions.toPrettyDate
import fr.thomasbernard03.tarot.commons.extensions.toPrettyTime
import fr.thomasbernard03.tarot.presentation.components.ActionButton
import fr.thomasbernard03.tarot.presentation.components.Loader
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(state : HistoryState, onEvent : (HistoryEvent) -> Unit) {

    LaunchedEffect(Unit) {
        onEvent(HistoryEvent.OnGetGames)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var selectedGameId by remember { mutableStateOf<Long?>(null) }
    val gameSheetState = rememberModalBottomSheetState()
    val sheetScope = rememberCoroutineScope()

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
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                items(state.games, key = { it.id }) { game ->

                    var expanded by rememberSaveable { mutableStateOf(false) }
                    val offsetX by animateDpAsState(targetValue = if (expanded) 0.dp else 16.dp)
                    val offsetY by animateDpAsState(targetValue = if (expanded) 36.dp else 0.dp)

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .combinedClickable(
                                onClick = {
                                    expanded = !expanded
                                },
                                onLongClick = {
                                    selectedGameId = game.id
                                }
                            )
                    ) {
                        Column(
                            modifier= Modifier.padding(LargePadding)
                        ) {
                            Text(text = "${game.rounds.size}")
                            Text(text = "Commencée le ${game.startedAt.toPrettyDate()} à ${game.startedAt.toPrettyTime()}")

                            if (game.finishedAt != null){
                                Text(text = "Terminée le ${game.finishedAt.toPrettyDate()} à ${game.finishedAt.toPrettyTime()}")
                            }
                            else {
                                Text(text = "En cours")
                            }
                            Box {
                                game.players.forEachIndexed { index, player ->
                                    Box(
                                        modifier = Modifier
                                            .offset(x = offsetX * index, y = offsetY * index)
                                            .zIndex(game.players.size - index.toFloat())
                                            .shadow(1.dp, shape = CircleShape, clip = true)
                                    ) {
                                        PlayerIcon(
                                            modifier = Modifier.size(28.dp),
                                            name = player.name,
                                            color = player.color.toColor(),
                                            style = LocalTextStyle.current.copy(fontSize = 12.sp)
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
}