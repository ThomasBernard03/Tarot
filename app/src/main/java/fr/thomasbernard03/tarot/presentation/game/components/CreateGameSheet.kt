package fr.thomasbernard03.tarot.presentation.game.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.commons.toColor
import fr.thomasbernard03.tarot.domain.models.Player
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon
import fr.thomasbernard03.tarot.presentation.components.PreviewComponent

@Composable
fun CreateGameSheet(
    players : List<Player>,
    onPlayersChanged : (List<Player>) -> Unit,
    onDismiss: () -> Unit,
    onCreateNewPlayer : () -> Unit,
    onValidate: (List<Player>) -> Unit
) {
    var editedPlayerId by remember { mutableLongStateOf(-1) }
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier.padding(LargePadding)
    ) {
        Text(
            text = stringResource(id = R.string.create_new_game_sheet_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = LargePadding)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(MediumPadding)
        ) {
            players.forEach { player ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MediumPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    if (editedPlayerId == player.id){
                        var editedPlayerName by remember { mutableStateOf(player.name) }

                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                        }

                        PlayerIcon(name = editedPlayerName, color =  player.color.toColor())

                        OutlinedTextField(
                            modifier = Modifier.focusRequester(focusRequester),
                            value = editedPlayerName,
                            onValueChange = { editedPlayerName = it },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    onPlayersChanged(
                                        players.map {
                                            if (it.id == player.id) {
                                                it.copy(name = editedPlayerName)
                                            } else {
                                                it
                                            }
                                        }
                                    )
                                    editedPlayerId = -1
                                }
                            )
                        )
                    }
                    else {
                        PlayerIcon(name = player.name, color = player.color.toColor())

                        Text(
                            text = player.name,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    TextButton(onClick = { editedPlayerId = player.id}) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit),
                            contentDescription = stringResource(id = R.string.create_new_game_sheet_edit_player)
                        )
                    }

                    TextButton(onClick = { onPlayersChanged(players.filter { it.id != player.id })}) {
                        Icon(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = stringResource(id = R.string.create_new_game_sheet_delete_player)
                        )
                    }
                }
                HorizontalDivider()
            }
        }

        TextButton(
            enabled = players.size < 5,
            onClick = { onCreateNewPlayer() }
        ) {
            Text(text = stringResource(id = R.string.create_new_game_sheet_add_new_player))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = { onValidate(players) },
                enabled = players.size >= 3
            ) {
                Text(text = stringResource(id = R.string.create_new_game_sheet_validate))
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun CreateGameSheetPreview() = PreviewComponent {
    val players = listOf(
        Player(1, "Thomas", PlayerColor.BLUE),
        Player(2, "Marianne", PlayerColor.ORANGE),
    )
    CreateGameSheet(
        players = players,
        onPlayersChanged = {},
        onDismiss = {},
        onValidate = {},
        onCreateNewPlayer = {}
    )
}