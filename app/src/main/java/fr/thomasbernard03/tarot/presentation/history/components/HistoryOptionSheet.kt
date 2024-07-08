package fr.thomasbernard03.tarot.presentation.history.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding
import fr.thomasbernard03.tarot.presentation.components.ActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HistoryOptionSheet(
    canResumeGame : Boolean,
    onResumeGame : suspend CoroutineScope.() -> Unit,
    onDeleteGame : suspend CoroutineScope.() -> Unit
) {
    val sheetScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.padding(bottom = LargePadding)
    ) {
        ActionButton(
            enabled = canResumeGame,
            modifier = Modifier.fillMaxWidth(),
            title = R.string.resume_game,
            icon = R.drawable.card,
            onClick = {
                sheetScope.launch { onResumeGame() }
            }
        )

        ActionButton(
            modifier = Modifier.fillMaxWidth(),
            title = R.string.delete_round,
            icon = R.drawable.bin,
            onClick = { sheetScope.launch { onDeleteGame() } }
        )
    }
}