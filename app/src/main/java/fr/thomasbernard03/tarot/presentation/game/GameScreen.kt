package fr.thomasbernard03.tarot.presentation.game

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.commons.calculateDefenderScore
import fr.thomasbernard03.tarot.commons.calculatePartnerScore
import fr.thomasbernard03.tarot.commons.calculateTakerScore
import fr.thomasbernard03.tarot.commons.toColor
import fr.thomasbernard03.tarot.domain.models.GameModel
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.presentation.components.Loader
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon
import fr.thomasbernard03.tarot.presentation.components.PreviewScreen
import fr.thomasbernard03.tarot.presentation.game.components.CreateGameSheet
import fr.thomasbernard03.tarot.presentation.theme.Green
import fr.thomasbernard03.tarot.presentation.theme.Red
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(state : GameState, onEvent : (GameEvent) -> Unit){
    val createGameBottomSheetState = rememberModalBottomSheetState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(state.showCreateGameSheet) {
        if (state.showCreateGameSheet){
            createGameBottomSheetState.show()
        } else {
            createGameBottomSheetState.hide()
        }
    }

    LaunchedEffect(Unit) {
        onEvent(GameEvent.OnGetCurrentGame)
    }

    if (state.showCreateGameSheet){
        ModalBottomSheet(
            onDismissRequest = { onEvent(GameEvent.OnCloseCreateDialogSheet) },
            sheetState = createGameBottomSheetState,
        ) {
            CreateGameSheet(
                onValidate = { onEvent(GameEvent.OnValidateCreateGameSheet(it)) },
            )
        }
    }

    Scaffold(
        topBar = {
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
                                contentDescription = stringResource(id = R.string.share_history)
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Terminer la partie") },
                            onClick = { }
                        )
                        DropdownMenuItem(
                            text = { Text("Save") },
                            onClick = { }
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
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
                Column {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = LargePadding)
                    ) {
                        state.currentGame.players.forEachIndexed { index, player ->
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally){

                                PlayerIcon(
                                    name = player.name,
                                    color = player.color.toColor()
                                )

                                Text(
                                    text = player.name,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    LazyVerticalGrid(
                        contentPadding = PaddingValues(LargePadding),
                        modifier = Modifier.fillMaxSize(),
                        columns = GridCells.Fixed(state.currentGame.players.size)
                    ) {

                        state.currentGame.rounds.forEach { round ->
                            val takerScore =
                                calculateTakerScore(round.points, round.bid, round.oudlers.size, state.currentGame.players.size, round.taker.id == round.calledPlayer?.id)

                            state.currentGame.players.forEach { player ->

                                val score =
                                    if (player.id == round.taker.id) takerScore
                                    else if (player.id == round.calledPlayer?.id) calculatePartnerScore(takerScore)
                                    else calculateDefenderScore(takerScore, state.currentGame.players.size)

                                item {
                                    Text(
                                        modifier = Modifier.padding(vertical = MediumPadding),
                                        text = score.toString(),
                                        textAlign = TextAlign.Center,
                                        fontWeight = if (player.id == round.taker.id) FontWeight.Bold else FontWeight.Normal,
                                        color = if (score >= 0) Green else Red
                                    )
                                }
                            }
                        }
                    }
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
        startedAt = Date(),
        finishedAt = null,
        players = listOf(
            PlayerModel(id = 1, name = "Thomas", color = PlayerColor.BLUE),
            PlayerModel(id = 1, name = "Marianne", color = PlayerColor.RED),
            PlayerModel(id = 1, name = "Nanou", color = PlayerColor.GREEN),
        ),
        rounds = listOf()
    )
    val state = GameState(currentGame = game, loadingGame = false)
    GameScreen(state = state, onEvent = {})
}