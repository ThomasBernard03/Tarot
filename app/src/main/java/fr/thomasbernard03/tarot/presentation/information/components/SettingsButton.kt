package fr.thomasbernard03.tarot.presentation.information.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.unit.dp
import fr.thomasbernard03.tarot.commons.LargePadding

@Composable
fun SettingsButton(
    modifier : Modifier = Modifier,
    @StringRes title : Int,
    subTitle : String? = null,
    @DrawableRes icon : Int,
    onClick : () -> Unit
) {
    Button(
        modifier = Modifier.height(70.dp),
        shape = RectangleShape,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground
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