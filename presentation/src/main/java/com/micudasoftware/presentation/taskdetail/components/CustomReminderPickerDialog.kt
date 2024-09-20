package com.micudasoftware.presentation.taskdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.micudasoftware.presentation.R
import com.micudasoftware.presentation.common.theme.PlanWiseTheme
import com.micudasoftware.presentation.taskdetail.components.model.ReminderModel
import java.time.temporal.ChronoUnit

@Composable
fun CustomReminderPickerDialog(
    onConfirm: (ReminderModel) -> Unit,
    onDismiss: () -> Unit,
) {
    var value by rememberSaveable { mutableStateOf("10") }
    var selectedUnit by rememberSaveable { mutableStateOf(ChronoUnit.MINUTES) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.medium
            )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(R.string.text_custom_reminder),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                value = value,
                onValueChange = {
                    if (it.isEmpty() ||
                        (it.toIntOrNull() != null &&
                                ((selectedUnit == ChronoUnit.MINUTES && it.toInt() < 60) ||
                                        (selectedUnit == ChronoUnit.HOURS && it.toInt() < 24) ||
                                        (selectedUnit == ChronoUnit.DAYS && it.toInt() < 30)))
                    ) {
                        value = it
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            ReminderPickerDialogRow(
                title = pluralStringResource(R.plurals.text_minutes_before_without_value, value.toIntOrNull() ?: 10),
                selected = selectedUnit == ChronoUnit.MINUTES,
                onClick = { selectedUnit = ChronoUnit.MINUTES },
                modifier = Modifier.fillMaxWidth()
            )
            ReminderPickerDialogRow(
                title = pluralStringResource(R.plurals.text_hours_before_without_value, value.toIntOrNull() ?: 10),
                selected = selectedUnit == ChronoUnit.HOURS,
                onClick = { selectedUnit = ChronoUnit.HOURS },
                modifier = Modifier.fillMaxWidth()
            )
            ReminderPickerDialogRow(
                title = pluralStringResource(R.plurals.text_days_before_without_value, value.toIntOrNull() ?: 10),
                selected = selectedUnit == ChronoUnit.DAYS,
                onClick = { selectedUnit = ChronoUnit.DAYS },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(onClick = onDismiss) {
                    Text(text = stringResource(R.string.button_cancel))
                }
                Button(
                    modifier = Modifier.padding(start = 8.dp),
                    enabled = value.toIntOrNull() != null,
                    onClick = { onConfirm(ReminderModel(value = value.toInt(), selectedUnit)) }
                ) {
                    Text(text = stringResource(R.string.button_confirm))
                }
            }
        }
    }
}

@Preview(locale = "sk")
@Composable
private fun CustomReminderPickerDialogPreview() {
    PlanWiseTheme {
        CustomReminderPickerDialog(onConfirm = {}, onDismiss = {})
    }
}