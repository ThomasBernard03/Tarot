package fr.thomasbernard03.tarot.presentation.player.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.extensions.toColor
import fr.thomasbernard03.tarot.presentation.components.Loader
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon
import fr.thomasbernard03.tarot.presentation.player.components.PlayerColorPicker
import fr.thomasbernard03.tarot.presentation.player.players.PlayersEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    id : Long,
    state : PlayerState,
    onEvent : (PlayerEvent) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var editing by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        onEvent(PlayerEvent.OnLoadPlayer(id))
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            MediumTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onEvent(PlayerEvent.OnGoBack) }) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = stringResource(id = R.string.go_back))
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.player_page_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    if (!editing){
                        IconButton(onClick = { editing = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit),
                                contentDescription = stringResource(id = R.string.edit_player)
                            )
                        }
                        IconButton(onClick = {  }) {
                            Icon(
                                painter = painterResource(id = R.drawable.bin),
                                contentDescription = stringResource(id = R.string.delete_player)
                            )
                        }
                    }
                },
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (state.loadingPlayer) {
                Loader(message = R.string.loading_player)
            }
            else if (state.player != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(LargePadding),
                    verticalArrangement = Arrangement.spacedBy(LargePadding)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        PlayerIcon(
                            modifier = Modifier.size(88.dp),
                            name = state.playerName.ifEmpty { state.player.name },
                            color = state.playerColor?.toColor() ?: state.player.color.toColor(),
                            style = LocalTextStyle.current.copy(fontSize = 48.sp)
                        )
                    }

                    PlayerColorPicker(
                        color = state.playerColor,
                        enabled = editing,
                        onColorSelected = { onEvent(PlayerEvent.OnPlayerColorChanged(it)) }
                    )
                }
            }
        }
    }
}