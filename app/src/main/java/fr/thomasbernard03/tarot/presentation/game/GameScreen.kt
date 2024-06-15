package fr.thomasbernard03.tarot.presentation.game

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.domain.models.GameModel
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.presentation.components.Loader
import fr.thomasbernard03.tarot.presentation.components.PreviewScreen
import fr.thomasbernard03.tarot.presentation.game.components.CreateGameSheet
import fr.thomasbernard03.tarot.presentation.game.components.NewRoundSheet
import fr.thomasbernard03.tarot.presentation.game.components.PlayerButton
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(state : GameState, onEvent : (GameEvent) -> Unit){
    val createGameBottomSheetState = rememberModalBottomSheetState()
    val createRoundBottomSheetState = rememberModalBottomSheetState()

    LaunchedEffect(state.showCreateGameSheet) {
        if (state.showCreateGameSheet){
            createGameBottomSheetState.show()
        } else {
            createGameBottomSheetState.hide()
        }
    }

    LaunchedEffect(state.showCreateRoundSheet) {
        if (state.showCreateRoundSheet){
            createRoundBottomSheetState.show()
        } else {
            createRoundBottomSheetState.hide()
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
    else if (state.showCreateRoundSheet && state.currentGame != null){
        ModalBottomSheet(
            onDismissRequest = { onEvent(GameEvent.OnCloseNewRoundSheet) },
            sheetState = createGameBottomSheetState,
        ) {
             NewRoundSheet(
                 players = state.currentGame.players,
                 onValidate = {  }
             )
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ){
                state.currentGame.players.forEach {
                    PlayerButton(
                        modifier = Modifier.height(70.dp),
                        name = it.name,
                        color = it.color,
                        onClick = {

                        }
                    )
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
                onClick = { onEvent(GameEvent.OnOpenNewRoundSheet) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = stringResource(id = R.string.new_round)
                )
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
    val state = GameState(currentGame = game)
    GameScreen(state = state, onEvent = {})
}