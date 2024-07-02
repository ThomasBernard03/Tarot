package fr.thomasbernard03.tarot.presentation.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.extensions.toColor
import fr.thomasbernard03.tarot.presentation.components.ActionButton
import fr.thomasbernard03.tarot.presentation.components.Loader
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon
import fr.thomasbernard03.tarot.presentation.game.GameEvent
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
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = {

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
                            Box {
                                game.players.forEachIndexed { index, player ->
                                    Box(
                                        modifier = Modifier
                                            .offset(x = 24.dp * index)
                                            .zIndex(game.players.size - index.toFloat())
                                            .shadow(1.dp, shape = CircleShape, clip = true)
                                    ) {
                                        PlayerIcon(
                                            name = player.name,
                                            color = player.color.toColor()
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