package com.micudasoftware.presentation.agenda.viewmodel

import com.micudasoftware.presentation.common.UIState
import com.micudasoftware.presentation.agenda.component.model.AgendaDayModel
import com.micudasoftware.presentation.agenda.component.model.AgendaItemModel

/**
 * State for the Agenda screen.
 *
 * @param month The month of the agenda.
 * @param year The year of the agenda.
 * @param days The days of the agenda.
 * @param tasks The items of the agenda.
 */
data class AgendaScreenState(
    val month: String = "",
    val year: String = "",
    val days: List<AgendaDayModel> = emptyList(),
    val tasks: List<AgendaItemModel> = emptyList()
): UIState
