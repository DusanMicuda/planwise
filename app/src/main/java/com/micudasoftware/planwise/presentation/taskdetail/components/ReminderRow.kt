package com.micudasoftware.planwise.presentation.taskdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.micudasoftware.planwise.ui.theme.Gray
import com.micudasoftware.planwise.ui.theme.LightGray
import com.micudasoftware.planwise.ui.theme.PlanWiseTheme

/**
 * A composable that displays a row with a reminder.
 *
 * @param title The title of the reminder.
 * @param isEditable Whether the reminder is editable.
 * @param onClick The action to perform when the reminder is clicked.
 * @param modifier The modifier to apply to this layout.
 */
@Composable
fun ReminderRow(
    title: String,
    isEditable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isEditable, onClick = onClick)
            .then(modifier),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
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
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Edit"
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
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
            isEditable = true,
            onClick = {}
        )
    }
}