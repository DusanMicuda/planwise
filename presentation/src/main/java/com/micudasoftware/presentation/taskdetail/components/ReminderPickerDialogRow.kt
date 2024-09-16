package com.micudasoftware.presentation.taskdetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.micudasoftware.presentation.common.theme.PlanWiseTheme

@Composable
fun ReminderPickerDialogRow(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(text = title)
    }
}

@Preview
@Composable
private fun ReminderPickerDialogRowPreview() {
    PlanWiseTheme {
        ReminderPickerDialogRow(
            modifier = Modifier.fillMaxWidth(),
            title = "5 minutes before",
            selected = false,
            onClick = {},
        )
    }
}