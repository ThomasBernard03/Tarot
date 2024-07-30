package fr.thomasbernard03.tarot.commons.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import domain.models.Oudler
import fr.thomasbernard03.tarot.R

@Composable
fun Oudler.toText() : String {
    val resourceId =
        when(this){
            Oudler.EXCUSE -> R.string.excuse
            Oudler.PETIT -> R.string.petit
            Oudler.GRAND -> R.string.grand
        }

    return stringResource(id = resourceId)
}

@Composable
fun Oudler.toIndicator() : String {
    val resourceId =
        when(this){
            Oudler.EXCUSE -> R.string.excuse_indicator
            Oudler.PETIT -> R.string.petit_indicator
            Oudler.GRAND -> R.string.grand_indicator
        }

    return stringResource(id = resourceId)
}