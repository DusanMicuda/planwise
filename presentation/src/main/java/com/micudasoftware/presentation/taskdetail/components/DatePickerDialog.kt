package com.micudasoftware.presentation.taskdetail.components

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.micudasoftware.presentation.common.theme.PlanWiseTheme
import com.micudasoftware.presentation.taskdetail.components.model.DatePickerDialogState
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    state: DatePickerDialogState,
    modifier: Modifier = Modifier,
) {
    val datePickerState = rememberDatePickerState()

    androidx.compose.material3.DatePickerDialog(
        modifier = modifier,
        onDismissRequest = state.onDismiss,
        confirmButton = {
            Button(
                enabled = datePickerState.selectedDateMillis != null,
                onClick = {
                    datePickerState.selectedDateMillis?.let { dateMillis ->
                        state.onConfirm(
                            OffsetDateTime.ofInstant(
                                Instant.ofEpochMilli(dateMillis),
                                ZoneId.systemDefault()
                            )
                        )
                    }
                }
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = state.onDismiss) {
                Text(text = "Cancel")
            }
        },
        shape = MaterialTheme.shapes.medium,
    ) {
        DatePicker(state = datePickerState)
    }
}

@Preview
@Composable
private fun DatePickerDialogPreview() {
    PlanWiseTheme {
        DatePickerDialog(
            state = DatePickerDialogState(
                onConfirm = {},
                onDismiss = {}
            )
        )
    }
}