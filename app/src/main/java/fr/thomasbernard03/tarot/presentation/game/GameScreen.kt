package fr.thomasbernard03.tarot.presentation.game

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.presentation.components.Loader
import fr.thomasbernard03.tarot.presentation.components.PreviewScreen
import fr.thomasbernard03.tarot.presentation.game.components.CreateGameSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(state : GameState, onEvent : (GameEvent) -> Unit){
    val modalBottomSheetState = rememberModalBottomSheetState()

    LaunchedEffect(state.showCreateGameSheet) {
        if (state.showCreateGameSheet){
            modalBottomSheetState.show()
        } else {
            modalBottomSheetState.hide()
        }
    }

    if (state.showCreateGameSheet){
        ModalBottomSheet(
            onDismissRequest = { onEvent(GameEvent.OnCloseCreateDialogSheet) },
            sheetState = modalBottomSheetState,
        ) {
            CreateGameSheet(
                onValidate = { onEvent(GameEvent.OnValidateCreateGameSheet(it)) },
            )
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {

        if (state.loadingGame){
            Loader(message = R.string.loading_current_game)
        }


        if (!state.loadingGame){
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