package com.micudasoftware.presentation.agenda.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.micudasoftware.presentation.R
import com.micudasoftware.presentation.common.theme.PlanWiseTheme

/**
 * A composable that displays a drop-down button.
 *
 * @param text The text to display on the button
 * @param onClick The action to perform when the button is clicked
 * @param modifier The modifier to apply to this layout
 * @param textColor The color of the text
 */
@Composable
fun DropDownButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Unspecified,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.labelMedium
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                tint = textColor,
                contentDescription = stringResource(R.string.button_more)
            )
        }
    }
}

/**
 * Preview for [DropDownButton].
 */
@Preview
@Composable
private fun DropDownButtonPreview() {
    PlanWiseTheme {
        DropDownButton(text = "November", onClick = {})
    }
}