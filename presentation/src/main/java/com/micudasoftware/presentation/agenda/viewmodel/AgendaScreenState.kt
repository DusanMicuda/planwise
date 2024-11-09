package com.micudasoftware.presentation.agenda.viewmodel

import com.micudasoftware.presentation.common.UIState
import com.micudasoftware.presentation.agenda.component.model.AgendaDayModel
import com.micudasoftware.presentation.agenda.component.model.AgendaItemModel
import com.micudasoftware.presentation.agenda.component.model.AgendaMonthModel
import com.micudasoftware.presentation.agenda.component.model.AgendaYearModel
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * State for the Agenda screen.
 *
 * @param years The available years for the agenda.
 * @param months The available months for the agenda.
 * @param days The available days for the agenda.
 * @param tasks The items of the agenda.
 */
data class AgendaScreenState(
    val years: List<AgendaYearModel> = initYears(),
    val months: List<AgendaMonthModel>  = initMonths(),
    val days: List<AgendaDayModel> = emptyList(),
    val tasks: List<AgendaItemModel> = emptyList()
): UIState {
    val date: OffsetDateTime = run {
        val day = days.find { it.selected }?.dayNumber
        val month = months.find { it.selected }?.value
        val year = years.find { it.selected }?.year
        if (day != null && month != null && year != null) {
            OffsetDateTime.of(year, month, day, 0, 0, 0, 0, OffsetDateTime.now().offset)
        } else {
            OffsetDateTime.now()
        }
    }

    private companion object {
        /**
         * Initializes the list of years for the agenda.
         *
         * @return The list of years.
         */
        private fun initYears(): List<AgendaYearModel> {
            val currentDate = OffsetDateTime.now()
            return (currentDate.year - 10..currentDate.year + 10).map {
                AgendaYearModel(
                    year = it,
                    selected = currentDate.year == it,
                )
            }
        }

        /**
         * Initializes the list of months for the agenda.
         *
         * @return The list of months.
         */
        private fun initMonths(): List<AgendaMonthModel> {
            val currentDate = OffsetDateTime.now()
            return  (1..12).map {
                val formatter = DateTimeFormatter.ofPattern("LLLL", Locale.getDefault())
                AgendaMonthModel(
                    name = currentDate.withMonth(it).format(formatter),
                    value = it,
                    selected = currentDate.monthValue == it,
                )
            }
        }
    }
}
