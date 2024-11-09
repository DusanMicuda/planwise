package com.micudasoftware.presentation.agenda.component.model

/**
 * Represents a model for a year in the agenda.
 *
 * @property year The numerical representation of the year.
 * @property selected Indicates whether the year is selected.
 */
data class AgendaYearModel(
    val year: Int,
    val selected: Boolean,
) {
    override fun toString(): String {
        return year.toString()
    }
}
