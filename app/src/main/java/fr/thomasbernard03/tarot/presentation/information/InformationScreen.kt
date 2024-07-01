package fr.thomasbernard03.tarot.presentation.information

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import fr.thomasbernard03.tarot.BuildConfig
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.presentation.information.components.SettingsButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen() {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

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
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            val uriHandler = LocalUriHandler.current

            SettingsButton(
                modifier = Modifier.fillMaxWidth(),
                title = R.string.open_source_code_settings_title,
                icon = R.drawable.github,
                onClick = {
                    uriHandler.openUri(BuildConfig.REPOSITORY_URL)
                }
            )

            SettingsButton(
                modifier = Modifier.fillMaxWidth(),
                title = R.string.report_bug_settings_title,
                icon = R.drawable.bug,
                onClick = {
                    uriHandler.openUri(BuildConfig.BUG_REPORT_URL)
                }
            )

            SettingsButton(
                modifier = Modifier.fillMaxWidth(),
                title = R.string.application_version_settings_title,
                subTitle = stringResource(id = R.string.application_version_settings_subtitle, BuildConfig.VERSION_NAME),
                icon = R.drawable.android,
                onClick = {

                }
            )
        }
    }
}