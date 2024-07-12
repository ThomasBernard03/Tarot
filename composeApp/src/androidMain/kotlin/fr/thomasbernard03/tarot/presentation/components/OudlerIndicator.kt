package fr.thomasbernard03.tarot.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.thomasbernard03.tarot.commons.SmallPadding
import fr.thomasbernard03.tarot.commons.extensions.toIndicator
import fr.thomasbernard03.tarot.domain.models.Oudler

@Composable
fun OudlerIndicator(
    modifier : Modifier = Modifier,
    oudler : Oudler
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Box(
            modifier = Modifier
                .height(24.dp)
                .padding(horizontal = SmallPadding)
                .widthIn(min = 24.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.onBackground,
                text = oudler.toIndicator()
            )
        }

    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
private fun OudlerIndicatorPreview() = PreviewComponent {
    OudlerIndicator(oudler = Oudler.GRAND)
    OudlerIndicator(oudler = Oudler.PETIT)
    OudlerIndicator(oudler = Oudler.EXCUSE)
}
