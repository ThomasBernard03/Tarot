package fr.thomasbernard03.tarot.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun PreviewComponent(content : @Composable ColumnScope.() -> Unit) {
    PreviewScreen {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            content()
        }
    }
}