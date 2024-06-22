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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MAX_PLAYER
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.commons.toColor
import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon
import fr.thomasbernard03.tarot.presentation.components.PreviewComponent

@Composable
fun CreateGameSheet(
    onValidate: (List<CreatePlayerModel>) -> Unit
) {
    var editedPlayerIndex by remember { mutableIntStateOf(-1) }
    val focusRequester = remember { FocusRequester() }
    val players = remember { mutableStateListOf<CreatePlayerModel>() }
    val context = LocalContext.current

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
            players.forEachIndexed { index, player ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MediumPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    if (editedPlayerIndex == index){

                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                        }

                        PlayerIcon(name = player.name, color =  player.color.toColor())

                        OutlinedTextField(
                            modifier = Modifier.focusRequester(focusRequester),
                            value = player.name,
                            onValueChange = { players[index] = player.copy(name = it) },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    editedPlayerIndex = -1
                                    focusRequester.freeFocus()
                                }
                            ),
                            singleLine = true
                        )
                    }
                    else {
                        PlayerIcon(name = player.name, color = player.color.toColor())

                        Text(
                            text = player.name,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    TextButton(onClick = { editedPlayerIndex = index }) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit),
                            contentDescription = stringResource(id = R.string.create_new_game_sheet_edit_player)
                        )
                    }

                    TextButton(onClick = {
                        players.removeAt(index)
                        editedPlayerIndex = -1
                    }
                    ) {
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
            enabled = players.size < MAX_PLAYER,
            onClick = {
                players.add(
                    CreatePlayerModel(
                        name = "${context.getString(R.string.create_new_game_sheet_default_player_name)} ${players.size + 1}",
                        color = PlayerColor.entries.shuffled().first()
                    )
                )
                editedPlayerIndex = -1
            }
        ) {
            Text(text = stringResource(id = R.string.create_new_game_sheet_add_new_player))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = { onValidate(players) },
                enabled = players.size == 5
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
    CreateGameSheet { _ ->

    }
}