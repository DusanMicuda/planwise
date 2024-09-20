package com.micudasoftware.presentation.agenda.component.model

/**
 * Represents a model for a day in the agenda.
 *
 * @property dayString The string representation of the day.
 * @property dayNumber The numerical representation of the day.
 * @property selected Indicates whether the day is selected.
 * @property onClick The callback function to be invoked when the day is clicked.
 */
data class AgendaDayModel(
    val dayString: String,
    val dayNumber: Int,
    val selected: Boolean,
    val onClick: () -> Unit,
)