package fr.thomasbernard03.tarot.presentation.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding

@Composable
fun GameScreen(){
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.BottomEnd).padding(LargePadding),
            onClick = { }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = stringResource(id = R.string.new_game)
            )
        }
    }
}