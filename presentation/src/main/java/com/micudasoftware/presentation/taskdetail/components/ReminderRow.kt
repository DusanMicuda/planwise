package com.micudasoftware.presentation.taskdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.micudasoftware.presentation.common.theme.Gray
import com.micudasoftware.presentation.common.theme.LightGray
import com.micudasoftware.presentation.common.theme.PlanWiseTheme

/**
 * A composable that displays a row with a reminder.
 *
 * @param title The title of the reminder.
 * @param isEditable Whether the reminder is editable.
 * @param onRemove The action to perform when the remove button is clicked.
 * @param modifier The modifier to apply to this layout.
 */
@Composable
fun ReminderRow(
    title: String,
    isEditable: Boolean,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .background(color = LightGray, shape = MaterialTheme.shapes.extraSmall)
                    .padding(4.dp),
                imageVector = Icons.Outlined.Notifications,
                tint = Gray,
                contentDescription = "Reminder"
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                text = title,
                style = MaterialTheme.typography.bodyLarge,
            )
            if (isEditable) {
                IconButton(onClick = onRemove) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        tint = MaterialTheme.colorScheme.error,
                        contentDescription = "Remove"
                    )
                }
            }
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

/**
 * Preview for [ReminderRow].
 */
@Preview
@Composable
private fun ReminderRowPreview() {
    PlanWiseTheme {
        ReminderRow(
            title = "Remind me to buy milk",
            isEditable = false,
            onRemove = {}
        )
    }
}

/**
 * Preview for [ReminderRow] when it is editable.
 */
@Preview
@Composable
private fun ReminderRowEditablePreview() {
    PlanWiseTheme {
        ReminderRow(
            title = "Remind me to buy milk",
            isEditable = true,
            onRemove = {}
        )
    }
}