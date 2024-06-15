package fr.thomasbernard03.tarot.commons

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.domain.models.Bid

@Composable
fun Bid.toText() : String {
    val resourceId =
    when(this){
        Bid.SMALL -> R.string.small
        Bid.GUARD -> R.string.guard
        Bid.GUARD_WITHOUT -> R.string.guard_without
        Bid.GUARD_AGAINST -> R.string.guard_against
    }

    return stringResource(id = resourceId)
}