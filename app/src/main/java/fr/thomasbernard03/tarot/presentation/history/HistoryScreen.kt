package fr.thomasbernard03.tarot.presentation.history

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.domain.models.GameModel
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.presentation.components.ActionButton
import fr.thomasbernard03.tarot.presentation.components.Loader
import fr.thomasbernard03.tarot.presentation.components.PreviewScreen
import fr.thomasbernard03.tarot.presentation.history.components.historyList
import kotlinx.coroutines.launch
import java.util.Date

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
                historyList(
                    games = state.games,
                    onGameLongPressed = { selectedGameId = it }
                )
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