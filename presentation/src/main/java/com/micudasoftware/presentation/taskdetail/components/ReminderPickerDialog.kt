package com.micudasoftware.presentation.taskdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.micudasoftware.presentation.common.theme.PlanWiseTheme
import com.micudasoftware.presentation.taskdetail.components.model.ReminderModel
import java.time.temporal.ChronoUnit

@Composable
fun ReminderPickerDialog(
    onConfirm: (ReminderModel) -> Unit,
    onCustom: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.medium
            )
        ) {
            val rowModifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 16.dp)
            val models = listOf(
                ReminderModel(value = 0, ChronoUnit.MINUTES),
                ReminderModel(value = 5, ChronoUnit.MINUTES),
                ReminderModel(value = 10, ChronoUnit.MINUTES),
                ReminderModel(value = 30, ChronoUnit.MINUTES),
                ReminderModel(value = 1, ChronoUnit.HOURS),
                ReminderModel(value = 1, ChronoUnit.DAYS),
            )

            models.forEach { model ->
                var selected by rememberSaveable { mutableStateOf(false) }

                ReminderPickerDialogRow(
                    title = model.title,
                    selected = selected,
                    onClick = {
                        selected = true
                        onConfirm(model)
                  },
                    modifier = rowModifier
                )
            }

            var selected by rememberSaveable { mutableStateOf(false) }
            ReminderPickerDialogRow(
                modifier = rowModifier,
                selected = selected,
                title = "Custom",
                onClick = {
                    selected = true
                    onCustom()
                }
            )
        }
    }
}

@Preview
@Composable
private fun ReminderPickerDialogPreview() {
    PlanWiseTheme {
        ReminderPickerDialog(onConfirm = {}, onCustom = {}, onDismiss = {})
    }
}