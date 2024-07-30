package fr.thomasbernard03.tarot.presentation.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.thomasbernard03.tarot.R
import fr.thomasbernard03.tarot.commons.LargePadding

@Composable
fun ActionButton(
    modifier : Modifier = Modifier,
    enabled : Boolean = true,
    @StringRes title : Int,
    subTitle : String? = null,
    @DrawableRes icon : Int,
    onClick : () -> Unit
) {
    Button(
        enabled = enabled,
        modifier = Modifier.height(65.dp),
        shape = RectangleShape,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = Color.Transparent,
        )
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LargePadding)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = title)
            )

            Column {
                Text(text = stringResource(id = title))

                if (subTitle != null){
                    Text(
                        text = subTitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActionButtonPreview() = PreviewComponent {
    ActionButton(
        title = R.string.open_source_code_settings_title,
        icon = R.drawable.android,
        onClick = {}
    )
}
