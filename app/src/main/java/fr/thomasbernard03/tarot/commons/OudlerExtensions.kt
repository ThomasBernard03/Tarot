package fr.thomasbernard03.tarot.commons

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.domain.models.Oudler

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