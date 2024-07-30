package fr.thomasbernard03.tarot.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.thomasbernard03.tarot.R

@Composable
fun <S : Comparable<S>> AnimatedCounter(
    modifier: Modifier = Modifier,
    value: S,
    duration: Int = 100,
    content: @Composable() (AnimatedContentScope.(targetState: S) -> Unit)
) {
    AnimatedContent(
        modifier = modifier,
        targetState = value,
        transitionSpec = {
            if (targetState > initialState) {
                (slideInVertically(animationSpec = tween(durationMillis = duration)) { -it } + fadeIn(animationSpec = tween(durationMillis = duration)))
                    .togetherWith(slideOutVertically(animationSpec = tween(durationMillis = duration)) { it } + fadeOut(animationSpec = tween(durationMillis = duration)))
            } else {
                (slideInVertically(animationSpec = tween(durationMillis = duration)) { it } + fadeIn(animationSpec = tween(durationMillis = duration)))
                    .togetherWith(slideOutVertically(animationSpec = tween(durationMillis = duration)) { -it } + fadeOut(animationSpec = tween(durationMillis = duration)))
            }.using(SizeTransform(clip = false))
        },
        label = stringResource(id = R.string.counter)
    ) { state ->
        content(state)
    }
}

@Composable
@Preview
private fun AnimatedCounterPreview(){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)) {
        var counter by remember { mutableIntStateOf(0) }
        AnimatedCounter(value = counter){
            Text(
                text = it.toString(),
                fontSize = 72.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { counter-- }) {
                Text("Decrement")
            }
            Button(onClick = { counter++ }) {
                Text("Increment")
            }
        }
    }
}