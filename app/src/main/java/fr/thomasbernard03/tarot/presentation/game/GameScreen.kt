package fr.thomasbernard03.tarot.presentation.game

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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
import fr.thomasbernard03.tarot.domain.models.Bid
import fr.thomasbernard03.tarot.domain.models.GameModel
import fr.thomasbernard03.tarot.domain.models.Oudler
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.RoundModel
import fr.thomasbernard03.tarot.presentation.components.Loader
import fr.thomasbernard03.tarot.presentation.components.OudlerIndicator
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon
import fr.thomasbernard03.tarot.presentation.components.PreviewScreen
import fr.thomasbernard03.tarot.presentation.game.components.CreateGameSheet
import fr.thomasbernard03.tarot.presentation.game.components.roundList
import fr.thomasbernard03.tarot.presentation.theme.Green
import fr.thomasbernard03.tarot.presentation.theme.Red
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun GameScreen(state : GameState, onEvent : (GameEvent) -> Unit){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onEvent(GameEvent.OnGetCurrentGame)
    }

    if (state.showCreateGameSheet){
        CreateGameSheet(
            loadingPlayers = state.loadingPlayers,
            players = state.players,
            onDismiss = { onEvent(GameEvent.OnCloseCreateDialogSheet) },
            onValidate = { onEvent(GameEvent.OnValidateCreateGameSheet(it)) },
        )
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
                                contentDescription = null
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                ) {
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .padding(horizontal = SmallPadding, vertical = MediumPadding)
                        ) {
                            state.currentGame.players.forEach { player ->
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box {
                                        PlayerIcon(
                                            name = player.name,
                                            color = player.color.toColor()
                                        )

                                        Box(modifier = Modifier
                                            .offset(x = 8.dp)
                                            .zIndex(1f)
                                            .align(Alignment.BottomEnd)
                                            .background(MaterialTheme.colorScheme.onBackground, CircleShape)
                                            .size(24.dp)
                                            .border(
                                                1.dp,
                                                MaterialTheme.colorScheme.background,
                                                CircleShape
                                            )
                                        ){
                                            Text(
                                                modifier = Modifier.align(Alignment.Center),
                                                text = state.currentGame.rounds.count { it.taker.id == player.id }.toString(),
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.background,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                   roundList(state.currentGame.rounds)
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
                finishedAt = Date(),
            ),
        )
    )
    val state = GameState(currentGame = game, loadingGame = false)
    GameScreen(state = state, onEvent = {})
}