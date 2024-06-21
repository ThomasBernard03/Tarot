package fr.thomasbernard03.tarot.presentation.round

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.commons.MediumPadding
import fr.thomasbernard03.tarot.commons.toColor
import fr.thomasbernard03.tarot.commons.toText
import fr.thomasbernard03.tarot.domain.models.Bid
import fr.thomasbernard03.tarot.domain.models.Oudler
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.presentation.components.AnimatedCounter
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundScreen(
    gameId : Long,
    state : RoundState,
    onEvent: (RoundEvent) -> Unit
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(gameId) {
        onEvent(RoundEvent.OnGetPlayers(gameId))
    }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onEvent(RoundEvent.OnGoBack) }) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = stringResource(id = R.string.go_back))
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.create_new_round_sheet_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = { },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MediumPadding),
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            AnimatedCounter(value = state.score) {
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
                items(items = state.players, key = { it.id }){
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (state.taker == it) MaterialTheme.colorScheme.primary else Color.Transparent,
                            contentColor = if (state.taker == it) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            val taker = if (state.taker == it) null else it
                            onEvent(RoundEvent.OnTakerChanged(taker))
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

            AnimatedVisibility(visible = state.players.size == 5) {
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.player_called),
                        textAlign = TextAlign.Center
                    )

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = LargePadding),
                        horizontalArrangement = Arrangement.spacedBy(MediumPadding),
                    ) {
                        items(items = state.players, key = { it.id }){
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (state.calledPlayer == it) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    contentColor = if (state.calledPlayer == it) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary
                                ),
                                onClick = {
                                    val calledPlayer = if (state.calledPlayer == it) null else it
                                    onEvent(RoundEvent.OnCalledPlayerChanged(calledPlayer))
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
                            containerColor = if (state.bid == it) MaterialTheme.colorScheme.primary else Color.Transparent,
                            contentColor = if (state.bid == it) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            val bid = if (state.bid == it) null else it
                            onEvent(RoundEvent.OnBidChanged(bid))
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
                            containerColor = if (state.oudlers.contains(it)) MaterialTheme.colorScheme.primary else Color.Transparent,
                            contentColor = if (state.oudlers.contains(it)) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            onEvent(RoundEvent.OnOudlerSelected(it))
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

            Card(
                shape = RectangleShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = LargePadding),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.attack),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = state.numberOfPoints.toString(),
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 70.sp)
                        )
                    }

                    Text(text = (state.numberOfPoints - (91 - state.numberOfPoints)).toString())

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.defense),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = (91 - state.numberOfPoints).toString(),
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 70.sp)
                        )
                    }
                }


                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { onEvent(RoundEvent.OnNumberOfPointsChanged(state.numberOfPoints - 1)) }) {
                        Icon(painter = painterResource(id = R.drawable.minus), contentDescription = null)
                    }

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = LargePadding)
                    ) {
                        Slider(
                            value = state.numberOfPoints.toFloat(),
                            onValueChange = { onEvent(RoundEvent.OnNumberOfPointsChanged(it.toInt()))},
                            valueRange = 0f..91f,
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.onPrimary,
                                activeTrackColor = MaterialTheme.colorScheme.surface,
                                inactiveTrackColor = MaterialTheme.colorScheme.background
                            )
                        )
                    }

                    IconButton(onClick = { onEvent(RoundEvent.OnNumberOfPointsChanged(state.numberOfPoints + 1)) }) {
                        Icon(Icons.Filled.Add, contentDescription = null)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LargePadding),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { onEvent(RoundEvent.OnCreateRound(gameId, state.taker!!, state.bid!!, state.oudlers, state.numberOfPoints)) },
                    enabled = state.taker != null && state.bid != null
                ) {
                    Text(text = stringResource(id = R.string.add_turn))
                }
            }
        }
    }
}