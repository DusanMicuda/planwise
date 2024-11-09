package com.micudasoftware.presentation.taskdetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.micudasoftware.presentation.R
import com.micudasoftware.presentation.common.utils.padding
import com.micudasoftware.presentation.common.theme.PlanWiseTheme

/**
 * A composable that displays a row with a title, time and date.
 *
 * @param title The title of the row.
 * @param time The time to display.
 * @param date The date to display.
 * @param isEditable Whether the time and date are editable.
 * @param onEditTime The action to perform when the time is clicked.
 * @param onEditDate The action to perform when the date is clicked.
 * @param modifier The modifier to apply to this layout.

 */
@Composable
fun DateTimeRow(
    title: String,
    time: String,
    date: String,
    isEditable: Boolean,
    onEditTime: () -> Unit,
    onEditDate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.widthIn(50.dp),
            text = title,
            style = MaterialTheme.typography.bodyLarge,
        )
        Row(
            modifier = Modifier
                .clickable(enabled = isEditable, onClick = onEditTime)
                .padding(start = 4.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = time,
                style = MaterialTheme.typography.bodyLarge,

                )
            if (isEditable) {
                Spacer(modifier = Modifier.padding(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.button_edit)
                )
            }
        }
        Row(
            modifier = Modifier
                .clickable(enabled = isEditable, onClick = onEditDate)
                .padding(start = 4.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = date,
                style = MaterialTheme.typography.bodyLarge,
            )
            if (isEditable) {
                Spacer(modifier = Modifier.padding(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.button_edit)
                )
            }
        }
    }
}

/**
 * Preview for [DateTimeRow].
 */
@Preview
@Composable
private fun DateTimeRowPreview() {
    PlanWiseTheme {
        DateTimeRow(
            title = "From",
            time = "12:00",
            date = "July 12, 2021",
            isEditable = true,
            onEditTime = {},
            onEditDate = {}
        )
    }
}