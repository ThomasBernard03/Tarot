package fr.thomasbernard03.tarot.presentation.game.components

import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MAX_PLAYER
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.commons.toColor
import fr.thomasbernard03.tarot.commons.toText
import fr.thomasbernard03.tarot.domain.models.Bid
import fr.thomasbernard03.tarot.domain.models.CreatePlayerModel
import fr.thomasbernard03.tarot.domain.models.Oudler
import fr.thomasbernard03.tarot.domain.models.PlayerColor
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.presentation.components.AnimatedCounter
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon
import fr.thomasbernard03.tarot.presentation.components.PreviewComponent

@Composable
fun NewRoundSheet(
    modifier : Modifier = Modifier,
    players : List<PlayerModel>,
    onValidate: (PlayerModel, Bid, List<Oudler>, Int) -> Unit
) {
    var taker by remember { mutableStateOf<PlayerModel?>(null) }
    var bid by remember { mutableStateOf<Bid?>(null) }
    var oudlers = remember { mutableStateListOf<Oudler>() }
    var numberOfPoints by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(numberOfPoints) }

    fun calculateScore(): Int {
        val baseScore = when (bid) {
            Bid.SMALL -> 25
            Bid.GUARD -> 50
            Bid.GUARD_WITHOUT -> 100
            Bid.GUARD_AGAINST -> 200
            else -> 0
        }

        val oudlerPoints = when (oudlers.size) {
            0 -> 56
            1 -> 51
            2 -> 41
            3 -> 36
            else -> 0
        }

        val difference = numberOfPoints - oudlerPoints
        val score = baseScore + if (difference >= 0) difference else difference * 2

        return if (taker != null) score else 0
    }

    LaunchedEffect(taker, bid, oudlers.size, numberOfPoints) {
        score = calculateScore()
    }

    Column(modifier.verticalScroll(rememberScrollState())) {
        Text(
            text = stringResource(id = R.string.create_new_round_sheet_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = LargePadding, start = LargePadding)
        )

        AnimatedCounter(value = score) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = it.toString(),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.taker),
            textAlign = TextAlign.Center
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = LargePadding),
            horizontalArrangement = Arrangement.spacedBy(MediumPadding),
        ) {
            items(items = players, key = { it.id }){
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (taker == it) MaterialTheme.colorScheme.primary else Color.Transparent,
                        contentColor = if (taker == it) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        taker = if (taker == it) null else it
                    },
                    shape = RoundedCornerShape(8.dp),
                    border = ButtonDefaults.outlinedButtonBorder
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PlayerIcon(
                            name = it.name,
                            color = it.color.toColor()
                        )

                        Text(text = it.name)
                    }
                }
            }
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.bid),
            textAlign = TextAlign.Center
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = LargePadding),
            horizontalArrangement = Arrangement.spacedBy(MediumPadding),
        ) {
            items(items = Bid.entries, key = { it.name }) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (bid == it) MaterialTheme.colorScheme.primary else Color.Transparent,
                        contentColor = if (bid == it) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        bid = if (bid == it) null else it
                    },
                    shape = RoundedCornerShape(8.dp),
                    border = ButtonDefaults.outlinedButtonBorder
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = it.toText())
                    }
                }
            }
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.oudlers),
            textAlign = TextAlign.Center
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = LargePadding),
            horizontalArrangement = Arrangement.spacedBy(MediumPadding),
        ) {
            items(items = Oudler.entries, key = { it.name }) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (oudlers.contains(it)) MaterialTheme.colorScheme.primary else Color.Transparent,
                        contentColor = if (oudlers.contains(it)) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        if (oudlers.contains(it)){
                            oudlers.remove(it)
                        } else {
                            oudlers.add(it)
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    border = ButtonDefaults.outlinedButtonBorder
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = it.toText())
                    }
                }
            }
        }


        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "${stringResource(id = R.string.number_of_points)} $numberOfPoints",
            textAlign = TextAlign.Center
        )

        Slider(
            modifier = Modifier.padding(horizontal = LargePadding),
            value = numberOfPoints.toFloat(),
            onValueChange = { numberOfPoints = it.toInt() },
            valueRange = 0f..91f
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LargePadding),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = {},
                enabled = taker != null && bid != null
            ) {
                Text(text = stringResource(id = R.string.create_new_game_sheet_validate))
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun NewRoundSheetPreview() = PreviewComponent {
    val players = listOf(
        PlayerModel(id = 1, name = "Thomas", color = PlayerColor.RED),
    )
    NewRoundSheet(players = players) { _, _, _, _ ->
    }
}