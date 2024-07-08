package fr.thomasbernard03.tarot.presentation.game.components

import android.content.res.Configuration
import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.commons.extensions.toColor
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.presentation.components.Loader
import fr.thomasbernard03.tarot.presentation.components.PlayerChip
import fr.thomasbernard03.tarot.presentation.components.PreviewComponent

@Composable
fun CreateGameSheet(
    loadingPlayers : Boolean = false,
    players : List<PlayerModel>,
    onDismiss: () -> Unit = {},
    onValidate: (List<PlayerModel>) -> Unit
) {
    val selectedPlayers = remember { mutableStateListOf<PlayerModel>() }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        dialogWindowProvider.window.setGravity(Gravity.BOTTOM)

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = LargePadding)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.create_new_game_sheet_title),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(LargePadding)
            )

            if (loadingPlayers){
                Loader(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    message = R.string.loading_players
                )
            }
            else {
                LazyVerticalGrid(
                    modifier = Modifier
                        .heightIn(min = 200.dp, max = 400.dp)
                        .fillMaxWidth(),
                    columns = GridCells.Adaptive(minSize = 120.dp),
                    contentPadding = PaddingValues(LargePadding),
                    horizontalArrangement = Arrangement.spacedBy(MediumPadding),
                    verticalArrangement = Arrangement.spacedBy(MediumPadding)
                ) {
                    items(players, key = { it.id }) { player ->
                        PlayerChip(
                            name = player.name,
                            color = player.color.toColor(),
                            onClick = {
                                if (selectedPlayers.contains(player))
                                    selectedPlayers.remove(player)
                                else
                                    selectedPlayers.add(player)
                            },
                            selected = selectedPlayers.contains(player)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { onValidate(selectedPlayers) },
                    enabled = selectedPlayers.size in 3..5
                ) {
                    Text(text = stringResource(id = R.string.create_new_game_sheet_validate))
                }
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun CreateGameSheetPreview() = PreviewComponent {
    CreateGameSheet(
        players = listOf(
            PlayerModel(id = 1, name = "Player 1", color = PlayerColor.RED),
            PlayerModel(id = 2, name = "Player 2", color = PlayerColor.BLUE),
            PlayerModel(id = 3, name = "Player 3", color = PlayerColor.GREEN),
        )
    ) { _ ->

    }
}