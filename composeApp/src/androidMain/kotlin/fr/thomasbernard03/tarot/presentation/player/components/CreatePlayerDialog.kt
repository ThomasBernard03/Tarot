package fr.thomasbernard03.tarot.presentation.player.components

import android.content.res.Configuration
import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import commons.PLAYER_NAME_MAX_LENGTH
import domain.models.PlayerColor
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.presentation.components.PreviewScreen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreatePlayerDialog(
    name : String,
    color : PlayerColor?,
    loading: Boolean,
    errorMessage : String,
    onNameChanged : (String) -> Unit,
    onColorSelected : (PlayerColor?) -> Unit,
    onDismiss: () -> Unit,
    onCreatePlayer: (name : String, color : PlayerColor) -> Unit
) {

    val focusRequester = remember { FocusRequester() }

    Dialog(onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        dialogWindowProvider.window.setGravity(Gravity.BOTTOM)

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(LargePadding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(MediumPadding)
        ) {
            Text(
                text = stringResource(id = R.string.create_player_dialog_title),
                style = MaterialTheme.typography.titleLarge
            )

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        focusRequester.requestFocus()
                    },
                value = name,
                onValueChange = {
                    if (it.length <= PLAYER_NAME_MAX_LENGTH)
                        onNameChanged(it)
                },
                label = { Text(text = stringResource(id = R.string.create_player_dialog_player_name_label)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.Words
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusRequester.freeFocus()
                    }
                ),
                singleLine = true,
                supportingText = {
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            PlayerColorPicker(color = color, onColorSelected = onColorSelected)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = LargePadding),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onCreatePlayer(name, color!!) },
                    enabled = !loading && name.isNotBlank() && color != null && errorMessage.isEmpty()
                ) {
                    Text(text = stringResource(id = R.string.create_player_dialog_confirmation))
                }
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun CreatePlayerDialogPreview() = PreviewScreen {
    CreatePlayerDialog(
        name = "Thomas",
        color = PlayerColor.entries.toTypedArray().random(),
        loading = false,
        errorMessage = "",
        onNameChanged = {},
        onColorSelected = {},
        onDismiss = {},
        onCreatePlayer = { _, _ -> }
    )
}