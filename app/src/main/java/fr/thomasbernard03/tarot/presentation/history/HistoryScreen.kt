package fr.thomasbernard03.tarot.presentation.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.extensions.toColor
import fr.thomasbernard03.tarot.presentation.components.Loader
import fr.thomasbernard03.tarot.presentation.components.PlayerIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(state : HistoryState, onEvent : (HistoryEvent) -> Unit) {

    LaunchedEffect(Unit) {
        onEvent(HistoryEvent.OnGetGames)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MediumTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.history_page_title),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            actions = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(id = R.string.clear_history)
                    )
                }

                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = stringResource(id = R.string.share_history)
                    )
                }
            },
            scrollBehavior = scrollBehavior
        )

        if (state.loading) {
            Loader(
                modifier = Modifier.fillMaxSize(),
                message = R.string.history_loading_message
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                items(state.games, key = { it.id }) { game ->
                    Card {
                        Text(
                            text = game.startedAt.toString(),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Box {
                            game.players.forEachIndexed { index, player ->
                                Box(
                                    modifier = Modifier
                                        .offset(x = 24.dp * index)
                                        .zIndex(game.players.size - index.toFloat())
                                        .shadow(1.dp, shape = CircleShape, clip = true)
                                ) {
                                    PlayerIcon(
                                        name = player.name,
                                        color = player.color.toColor()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}