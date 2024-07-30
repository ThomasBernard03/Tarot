package fr.thomasbernard03.tarot.commons.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import domain.models.Bid
import fr.thomasbernard03.tarot.R

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

@Composable
fun Bid.toTextIndicator() : String {
    val resourceId =
        when(this){
            Bid.SMALL -> R.string.small_indicator
            Bid.GUARD -> R.string.guard_indicator
            Bid.GUARD_WITHOUT -> R.string.guard_without_indicator
            Bid.GUARD_AGAINST -> R.string.guard_against_indicator
        }

    return stringResource(id = resourceId)
}