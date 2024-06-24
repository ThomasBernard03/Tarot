package fr.thomasbernard03.tarot.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.thomasbernard03.tarot.presentation.theme.TarotTheme

@Composable
fun PreviewScreen(content: @Composable () -> Unit) {
    TarotTheme {
        Scaffold {
            Surface(
                modifier = Modifier.fillMaxSize().padding(it),
                color = MaterialTheme.colorScheme.background
            ) {
                content()
            }
        }
    }
}