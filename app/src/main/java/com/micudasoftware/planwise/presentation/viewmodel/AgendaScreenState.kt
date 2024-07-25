package com.micudasoftware.planwise.presentation.viewmodel

import com.micudasoftware.planwise.presentation.common.UIState
import com.micudasoftware.planwise.presentation.component.model.AgendaDayModel
import com.micudasoftware.planwise.presentation.component.model.AgendaItemModel

/**
 * State for the Agenda screen.
 *
 * @param month The month of the agenda.
 * @param year The year of the agenda.
 * @param days The days of the agenda.
 * @param items The items of the agenda.
 */
data class AgendaScreenState(
    val month: String = "",
    val year: String = "",
    val days: List<AgendaDayModel> = emptyList(),
    val items: List<AgendaItemModel> = emptyList()
): UIState
