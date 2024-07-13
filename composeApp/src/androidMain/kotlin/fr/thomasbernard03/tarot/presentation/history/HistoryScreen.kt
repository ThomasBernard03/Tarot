package fr.thomasbernard03.tarot.presentation.history

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import commons.extensions.LocalDateTimeNow
import domain.models.GameModel
import domain.models.PlayerColor
import domain.models.PlayerModel
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.presentation.components.Loader
import fr.thomasbernard03.tarot.presentation.components.PreviewScreen
import fr.thomasbernard03.tarot.presentation.history.components.HistoryOptionSheet
import fr.thomasbernard03.tarot.presentation.history.components.historyList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(state : HistoryState, onEvent : (HistoryEvent) -> Unit) {

    LaunchedEffect(Unit) {
        onEvent(HistoryEvent.OnGetGames)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var selectedGameId by remember { mutableStateOf<Long?>(null) }
    val gameSheetState = rememberModalBottomSheetState()

    if (selectedGameId != null){
        ModalBottomSheet(
            sheetState = gameSheetState,
            onDismissRequest = { selectedGameId = null },
        ) {
            HistoryOptionSheet(
                canResumeGame = state.games.firstOrNull { it.id == selectedGameId }?.finishedAt != null,
                onResumeGame = {
                    onEvent(HistoryEvent.OnResumeGame(selectedGameId!!))
                    gameSheetState.hide()
                    selectedGameId = null
                },
                onDeleteGame = {
                    onEvent(HistoryEvent.OnDeleteGame(selectedGameId!!))
                    gameSheetState.hide()
                    selectedGameId = null
                }
            )
        }
    }

    Scaffold(
        topBar = {
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
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            if (state.loading) {
                Loader(
                    modifier = Modifier.align(Alignment.Center),
                    message = R.string.history_loading_message
                )
            }
            else if (state.games.isEmpty()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.empty_games_history_message)
                )
            }
            else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    contentPadding = PaddingValues(LargePadding),
                    verticalArrangement = Arrangement.spacedBy(MediumPadding)
                ) {
                    historyList(
                        games = state.games,
                        onGameLongPressed = { selectedGameId = it }
                    )
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
                startedAt = LocalDateTimeNow(),
                rounds = listOf(),
                players = listOf(
                    PlayerModel(id = 1, name = "Thomas", color = PlayerColor.GREEN),
                    PlayerModel(id = 2, name = "Marianne", color = PlayerColor.RED),
                    PlayerModel(id = 4, name = "Thibaut", color = PlayerColor.BLUE),
                ),
                finishedAt = LocalDateTimeNow()
            ),
            GameModel(
                id = 2,
                startedAt = LocalDateTimeNow(),
                rounds = listOf(),

                players = listOf(
                    PlayerModel(id = 1, name = "Thomas", color = PlayerColor.GREEN),
                    PlayerModel(id = 2, name = "Marianne", color = PlayerColor.RED),
                    PlayerModel(id = 4, name = "Thibaut", color = PlayerColor.BLUE),
                ),
                finishedAt = LocalDateTimeNow()
            ),
            GameModel(
                id = 3,
                startedAt = LocalDateTimeNow(),
                rounds = listOf(),
                players = listOf(
                    PlayerModel(id = 1, name = "Thomas", color = PlayerColor.GREEN),
                    PlayerModel(id = 2, name = "Marianne", color = PlayerColor.RED),
                    PlayerModel(id = 4, name = "Thibaut", color = PlayerColor.BLUE),
                ),
                finishedAt = LocalDateTimeNow()
            ),
        )
    )
    HistoryScreen(state = state, onEvent = {})
}