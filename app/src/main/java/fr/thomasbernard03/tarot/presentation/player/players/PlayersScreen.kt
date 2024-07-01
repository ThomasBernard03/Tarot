package fr.thomasbernard03.tarot.presentation.player.players

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.commons.extensions.toColor
import fr.thomasbernard03.tarot.presentation.components.Loader
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon
import fr.thomasbernard03.tarot.presentation.player.components.CreatePlayerDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersScreen(state : PlayersState, onEvent: (PlayersEvent) -> Unit) {

    LaunchedEffect(Unit) {
        onEvent(PlayersEvent.OnLoadPlayers)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
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
                columns = GridCells.Adaptive(minSize = 120.dp),
                contentPadding = PaddingValues(LargePadding),
                horizontalArrangement = Arrangement.spacedBy(MediumPadding),
                verticalArrangement = Arrangement.spacedBy(MediumPadding)
            ) {
                items(state.players, key = { it.id }) { player ->
                    Card(
                        shape = RoundedCornerShape(topStart = 25.dp, bottomStart = 25.dp, topEnd = 12.dp, bottomEnd = 12.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(MediumPadding)
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