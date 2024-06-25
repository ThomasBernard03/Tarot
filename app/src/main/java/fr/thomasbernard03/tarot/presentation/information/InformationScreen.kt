package fr.thomasbernard03.tarot.presentation.information

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.aghajari.compose.lazyswipecards.LazySwipeCards
import fr.thomasbernard03.tarot.R

@Composable
fun InformationScreen() {
    val cards by remember {
        mutableStateOf(
            mutableListOf(
                R.drawable.excuse,
                R.drawable.one,
                R.drawable.twenty_one,
            )
        )
    }

    LazySwipeCards {
        items(cards) { card ->
            Image(
                painter = painterResource(id = card),
                contentDescription = null
            )
        }
    }
}