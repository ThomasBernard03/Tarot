package fr.thomasbernard03.tarot.presentation.game

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import commons.extensions.LocalDateTimeNow
import domain.models.Bid
import domain.models.GameModel
import domain.models.Oudler
import domain.models.PlayerColor
import domain.models.PlayerModel
import domain.models.RoundModel
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.presentation.components.ActionButton
import fr.thomasbernard03.tarot.presentation.components.Loader
import fr.thomasbernard03.tarot.presentation.components.PreviewScreen
import fr.thomasbernard03.tarot.presentation.game.components.CreateGameSheet
import fr.thomasbernard03.tarot.presentation.game.components.playersScoreHeader
import fr.thomasbernard03.tarot.presentation.game.components.roundList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun GameScreen(state : GameState, onEvent : (GameEvent) -> Unit){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val snackbarHostState = remember { SnackbarHostState() }
    val errorScope = rememberCoroutineScope()
    val sheetScope = rememberCoroutineScope()
    val haptics = LocalHapticFeedback.current
    var selectedRoundId by remember { mutableStateOf<Long?>(null) }
    val roundSheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) {
        onEvent(GameEvent.OnGetCurrentGame)
    }

    fun onError(message : String){
        errorScope.launch {
            if (snackbarHostState.currentSnackbarData != null)
                snackbarHostState.currentSnackbarData!!.dismiss()

            snackbarHostState.showSnackbar(message, withDismissAction = true)
            onEvent(GameEvent.OnDismissMessage)
        }
    }

    LaunchedEffect(state.message) {
        if (state.message.isNotBlank())
            onError(state.message)
    }

    if (state.showCreateGameSheet){
        CreateGameSheet(
            loadingPlayers = state.loadingPlayers,
            players = state.players,
            onDismiss = { onEvent(GameEvent.OnCloseCreateDialogSheet) },
            onValidate = { onEvent(GameEvent.OnValidateCreateGameSheet(it)) },
        )
    }
    else if (selectedRoundId != null){
        ModalBottomSheet(
            sheetState = roundSheetState,
            onDismissRequest = { selectedRoundId = null },
        ) {
            Column(
                modifier = Modifier.padding(bottom = LargePadding)
            ) {
                ActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = R.string.edit_round,
                    icon = R.drawable.edit,
                    onClick = {
                        sheetScope.launch {
                            onEvent(GameEvent.OnEditRound(gameId = state.currentGame!!.id, roundId = selectedRoundId!!))
                            roundSheetState.hide()
                            selectedRoundId = null
                        }
                    }
                )

                ActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = R.string.delete_round,
                    icon = R.drawable.bin,
                    onClick = {
                        onEvent(GameEvent.OnDeleteRound(selectedRoundId!!))
                        sheetScope.launch {
                            roundSheetState.hide()
                            selectedRoundId = null
                        }
                    }
                )
            }
        }
    }

    Scaffold(
        topBar = {
            var expanded by remember { mutableStateOf(false) }

            MediumTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.current_game),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    if (state.currentGame != null){
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = stringResource(id = R.string.show_options)
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.finish_game)) },
                            onClick = {
                                state.currentGame?.id?.let { onEvent(GameEvent.OnFinishGame(it)) }
                                expanded = false
                            }
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {
            if (state.loadingGame){
                Loader(
                    modifier = Modifier.align(Alignment.Center),
                    message = R.string.loading_current_game
                )
            }
            else if(state.currentGame == null) {
                Text(
                    modifier = Modifier
                        .padding(LargePadding)
                        .align(Alignment.Center),
                    text = stringResource(id = R.string.no_current_game_message),
                    textAlign = TextAlign.Center
                )
            }
            else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 70.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                ) {
                    playersScoreHeader(state.currentGame)

                    roundList(
                        game = state.currentGame,
                        onRoundPressed = {
                            onEvent(GameEvent.OnGoToRoundDetail(gameId = state.currentGame.id, roundId = it))
                        },
                        onRoundLongPressed = {
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            selectedRoundId = it
                        }
                    )
                }
            }

            // Create a new game
            if (!state.loadingGame && state.currentGame == null){
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(LargePadding),
                    onClick = { onEvent(GameEvent.OnOpenCreateDialogSheet) }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = stringResource(id = R.string.new_game)
                    )
                }
            }
            else if (state.currentGame != null && state.currentGame.finishedAt == null){
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(LargePadding),
                    onClick = { onEvent(GameEvent.OnNewRoundButtonPressed(state.currentGame.id)) }) {

                    Icon(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = stringResource(id = R.string.new_round)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun GameScreenLoadingPreview() = PreviewScreen {
    val state = GameState(loadingGame = true)
    GameScreen(state = state, onEvent = {})
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun GameScreenPreview() = PreviewScreen {
    val state = GameState(loadingGame = false)
    GameScreen(state = state, onEvent = {})
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun GameScreenInProgressPreview() = PreviewScreen {
    val game = GameModel(
        id = 1,
        startedAt = LocalDateTimeNow(),
        finishedAt = null,
        players = listOf(
            PlayerModel(id = 1, name = "Thomas", color = PlayerColor.BLUE),
            PlayerModel(id = 2, name = "Marianne", color = PlayerColor.RED),
            PlayerModel(id = 3, name = "Nanou", color = PlayerColor.GREEN),
        ),
        rounds = listOf(
            RoundModel(
                id = 1,
                taker = PlayerModel(id = 1, name = "Thomas", color = PlayerColor.BLUE),
                calledPlayer =  PlayerModel(id = 2, name = "Marianne", color = PlayerColor.RED),
                oudlers = listOf(Oudler.GRAND),
                bid = Bid.GUARD,
                points = 58,
                finishedAt = LocalDateTimeNow(),
            ),
        )
    )
    val state = GameState(currentGame = game, loadingGame = false)
    GameScreen(state = state, onEvent = {})
}