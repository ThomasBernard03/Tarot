package fr.thomasbernard03.tarot.presentation.information

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import fr.thomasbernard03.tarot.BuildConfig
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.presentation.components.ActionButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen() {
    var showConfetti by remember { mutableStateOf(false) }
    val preloaderLottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = 1,
        isPlaying = showConfetti
    )

    if (preloaderProgress == 1f){
        showConfetti = false
    }


    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.settings_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            Column {
                val uriHandler = LocalUriHandler.current

                ActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = R.string.official_rules_settings_title,
                    icon = R.drawable.book,
                    onClick = {
                        uriHandler.openUri(BuildConfig.TAROT_OFFICIAL_RULES_URL)
                    }
                )

                ActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = R.string.open_source_code_settings_title,
                    icon = R.drawable.github,
                    onClick = {
                        uriHandler.openUri(BuildConfig.REPOSITORY_URL)
                    }
                )

                ActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = R.string.report_bug_settings_title,
                    icon = R.drawable.bug,
                    onClick = {
                        uriHandler.openUri(BuildConfig.BUG_REPORT_URL)
                    }
                )

                ActionButton(
                    enabled = !showConfetti,
                    modifier = Modifier.fillMaxWidth(),
                    title = R.string.application_version_settings_title,
                    subTitle = stringResource(id = R.string.application_version_settings_subtitle, BuildConfig.VERSION_NAME),
                    icon = R.drawable.android,
                    onClick = {
                        showConfetti = true
                    }
                )
            }

            if (showConfetti){
                LottieAnimation(
                    composition = preloaderLottieComposition,
                    progress = preloaderProgress,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}