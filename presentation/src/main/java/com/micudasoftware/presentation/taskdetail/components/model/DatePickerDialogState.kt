package com.micudasoftware.presentation.taskdetail.components.model

import java.time.OffsetDateTime

/**
 * Represents the state of a date picker dialog.
 *
 * @property onConfirm A lambda function to be called when the date is confirmed.
 * @property onDismiss A lambda function to be called when the dialog is dismissed.
 */
data class DatePickerDialogState(
    val onConfirm: (OffsetDateTime) -> Unit,
    val onDismiss: () -> Unit,
)
