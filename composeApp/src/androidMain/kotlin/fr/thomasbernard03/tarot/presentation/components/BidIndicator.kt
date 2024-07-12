package fr.thomasbernard03.tarot.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.thomasbernard03.tarot.commons.SmallPadding
import fr.thomasbernard03.tarot.commons.extensions.toTextIndicator
import fr.thomasbernard03.tarot.domain.models.Bid

@Composable
fun BidIndicator(
    modifier : Modifier = Modifier,
    bid : Bid
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Text(
            modifier = Modifier.padding(SmallPadding),
            text = bid.toTextIndicator()
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
private fun BidIndicatorPreview() = PreviewComponent {
    BidIndicator(bid = Bid.SMALL)
    BidIndicator(bid = Bid.GUARD)
    BidIndicator(bid = Bid.GUARD_WITHOUT)
    BidIndicator(bid = Bid.GUARD_AGAINST)
}
