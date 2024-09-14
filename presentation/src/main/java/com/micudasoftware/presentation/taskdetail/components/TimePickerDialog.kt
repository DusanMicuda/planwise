package com.micudasoftware.presentation.taskdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.micudasoftware.presentation.common.theme.PlanWiseTheme
import com.micudasoftware.presentation.taskdetail.components.model.TimePickerDialogState
import java.time.OffsetTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    state: TimePickerDialogState,
) {
    Dialog(onDismissRequest = state.onDismiss) {
        val currentTime = OffsetTime.now()
        val timePickerState = rememberTimePickerState(
            initialMinute = currentTime.minute,
            initialHour = currentTime.hour,
            is24Hour = true,
        )

        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.medium)
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            TimePicker(
                modifier = Modifier.fillMaxWidth(),
                state = timePickerState
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(onClick = state.onDismiss) {
                    Text(text = "Cancel")
                }
                Button(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = {
                        state.onConfirm(
                            OffsetTime.of(
                                timePickerState.hour,
                                timePickerState.minute,
                                0,
                                0,
                                currentTime.offset
                            )
                        )
                    }
                ) {
                    Text(text = "Confirm")
                }
            }
        }
    }
}

@Preview
@Composable
private fun TimePickerDialogPreview() {
    PlanWiseTheme {
        TimePickerDialog(
            state = TimePickerDialogState(
                onConfirm = {},
                onDismiss = {}
            )
        )
    }
}