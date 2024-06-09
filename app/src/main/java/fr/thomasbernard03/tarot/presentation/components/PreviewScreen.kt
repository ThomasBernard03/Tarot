package fr.thomasbernard03.tarot.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.thomasbernard03.tarot.presentation.theme.TarotTheme

@Composable
fun PreviewScreen(content: @Composable () -> Unit) {
    TarotTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}