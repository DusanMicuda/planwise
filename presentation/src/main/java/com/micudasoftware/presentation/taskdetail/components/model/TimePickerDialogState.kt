package com.micudasoftware.presentation.taskdetail.components.model

import java.time.OffsetTime

/**
 * Represents the state of a time picker dialog.
 *
 * @property onConfirm A lambda function to be called when the time is confirmed.
 * @property onDismiss A lambda function to be called when the dialog is dismissed.
 */
data class TimePickerDialogState(
    val onConfirm: (OffsetTime) -> Unit,
    val onDismiss: () -> Unit,
)
