package com.micudasoftware.presentation.agenda.component.model

/**
 * Represents a model for a month in the agenda.
 *
 * @property name The name of the month.
 * @property value The numerical representation of the month.
 * @property selected Indicates whether the month is selected.
 */
data class AgendaMonthModel(
    val name: String,
    val value: Int,
    val selected: Boolean,
) {
    override fun toString(): String {
        return name
    }
}
