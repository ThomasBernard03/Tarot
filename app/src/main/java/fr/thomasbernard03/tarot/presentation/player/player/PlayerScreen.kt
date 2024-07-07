package fr.thomasbernard03.tarot.presentation.player.player

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.extensions.toColor
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.presentation.components.Loader
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon
import fr.thomasbernard03.tarot.presentation.player.components.PlayerColorPicker
import fr.thomasbernard03.tarot.presentation.player.players.PlayersEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    id : Long,
    editing : Boolean,
    state : PlayerState,
    onEvent : (PlayerEvent) -> Unit
) {
    val errorScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var editing by remember { mutableStateOf(editing) }
    var editPlayerColor by remember(state.player) { mutableStateOf<PlayerColor?>(state.player?.color) }
    var editPlayerName by remember(state.player) { mutableStateOf(state.player?.name ?: "") }


    fun onError(message : String){
        errorScope.launch {
            if (snackbarHostState.currentSnackbarData != null)
                snackbarHostState.currentSnackbarData!!.dismiss()

            snackbarHostState.showSnackbar(message, withDismissAction = true)
            onEvent(PlayerEvent.OnDismissMessage)
        }
    }


    LaunchedEffect(state.message) {
        if (state.message.isNotBlank()) {
            onError(state.message)
        }
    }

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
                        IconButton(onClick = { onEvent(PlayerEvent.OnDeletePlayer(id)) }) {
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
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(LargePadding)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            PlayerIcon(
                                modifier = Modifier.size(88.dp),
                                name = editPlayerName.ifEmpty { state.player.name },
                                color = editPlayerColor?.toColor() ?: state.player.color.toColor(),
                                style = LocalTextStyle.current.copy(fontSize = 48.sp)
                            )
                        }

                        OutlinedTextField(
                            readOnly = !editing,
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = editPlayerName,
                            onValueChange = {
                                editPlayerName = it
                            },
                            label = { Text(text = stringResource(id = R.string.create_player_dialog_player_name_label)) },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            singleLine = true,
                        )

                        PlayerColorPicker(
                            color = editPlayerColor ?: state.player.color,
                            enabled = editing,
                            onColorSelected = { it.let { editPlayerColor = it }  }
                        )
                    }

                    AnimatedVisibility(visible = editing) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(
                                onClick = {
                                    editPlayerColor = state.player.color
                                    editPlayerName = state.player.name
                                    editing = false
                                }
                            ) {
                                Text(text = stringResource(id = R.string.cancel))
                            }

                            TextButton(
                                enabled = editPlayerName.isNotBlank() && editPlayerColor != null && (editPlayerName != state.player.name || editPlayerColor != state.player.color),
                                onClick = {
                                    onEvent(PlayerEvent.OnEditPlayer(id, editPlayerName, editPlayerColor!!))
                                    editing = false
                                }
                            ) {
                                Text(text = stringResource(id = R.string.validate))
                            }
                        }
                    }
                }
            }
        }
    }
}