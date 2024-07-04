package fr.thomasbernard03.tarot.presentation.player.players

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.commons.extensions.toColor
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.errors.player.DeletePlayerError
import fr.thomasbernard03.tarot.presentation.components.ActionButton
import fr.thomasbernard03.tarot.presentation.components.Loader
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon
import fr.thomasbernard03.tarot.presentation.components.PreviewScreen
import fr.thomasbernard03.tarot.presentation.game.GameEvent
import fr.thomasbernard03.tarot.presentation.player.components.CreatePlayerDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PlayersScreen(state : PlayersState, onEvent: (PlayersEvent) -> Unit) {

    LaunchedEffect(Unit) {
        onEvent(PlayersEvent.OnLoadPlayers)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    var selectedPlayerId by remember { mutableStateOf<Long?>(null) }
    val playerSheetState = rememberModalBottomSheetState()
    val sheetScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val errorScope = rememberCoroutineScope()

    fun onError(message : String){
        errorScope.launch {
            if (snackbarHostState.currentSnackbarData != null)
                snackbarHostState.currentSnackbarData!!.dismiss()

            snackbarHostState.showSnackbar(message, withDismissAction = true)
            onEvent(PlayersEvent.OnDismissMessage)
        }
    }


    LaunchedEffect(state.message) {
        if (state.message.isNotBlank()) {
            onError(state.message)
        }
    }

    if (state.showCreatePlayerDialog){
        CreatePlayerDialog(
            name = state.createPlayerDialogName,
            color = state.createPlayerDialogColor,
            loading = state.createPlayerDialogLoading,
            errorMessage = state.createPlayerDialogMessage,
            onNameChanged = { onEvent(PlayersEvent.OnCreatePlayerDialogNameChanged(it)) },
            onColorSelected = { onEvent(PlayersEvent.OnCreatePlayerDialogColorSelected(it)) },
            onDismiss = { onEvent(PlayersEvent.OnDismissCreatePlayerDialog) },
            onCreatePlayer = { name, color -> onEvent(PlayersEvent.OnCreatePlayerDialogValidated(name, color))}
        )
    }

    if (selectedPlayerId != null){
        ModalBottomSheet(
            sheetState = playerSheetState,
            onDismissRequest = { selectedPlayerId = null },
        ) {
            Column(
                modifier = Modifier.padding(bottom = LargePadding)
            ) {
                ActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = R.string.edit_player,
                    icon = R.drawable.edit,
                    onClick = {
                        sheetScope.launch {
                            playerSheetState.hide()
                            selectedPlayerId = null
                        }
                    }
                )

                ActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = R.string.delete_player,
                    icon = R.drawable.bin,
                    onClick = {
                        sheetScope.launch {
                            onEvent(PlayersEvent.OnDeletePlayer(selectedPlayerId!!))
                            playerSheetState.hide()
                            selectedPlayerId = null
                        }
                    }
                )
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.players_page_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { onEvent(PlayersEvent.OnShowCreatePlayerDialog) }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(id = R.string.create_new_player)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (state.loadingPlayers) {
                Loader(
                    modifier = Modifier.fillMaxSize(),
                    message = R.string.loading_players
                )
            }
            else {
                LazyVerticalGrid(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    columns = GridCells.Adaptive(minSize = 150.dp),
                    contentPadding = PaddingValues(LargePadding),
                    horizontalArrangement = Arrangement.spacedBy(MediumPadding),
                    verticalArrangement = Arrangement.spacedBy(MediumPadding)
                ) {
                    items(state.players, key = { it.id }) { player ->
                        Row(
                            modifier = Modifier.combinedClickable(
                                onClick = { },
                                onLongClick = { selectedPlayerId = player.id }
                            )
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.spacedBy(MediumPadding),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                PlayerIcon(
                                    name = player.name,
                                    color = player.color.toColor()
                                )
                                Text(text = player.name)
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
private fun PlayersScreenPreview() = PreviewScreen {
    val players = listOf(
        PlayerModel(id = 1, name = "Thomas", color = PlayerColor.GREEN),
        PlayerModel(id = 2, name = "Marianne", color = PlayerColor.RED),
        PlayerModel(id = 4, name = "Thibaut", color = PlayerColor.BLUE),
    )
    val state = PlayersState(players = players)
    PlayersScreen(state = state, onEvent = {})
}