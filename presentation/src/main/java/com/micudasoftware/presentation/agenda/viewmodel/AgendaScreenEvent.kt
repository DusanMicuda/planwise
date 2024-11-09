package com.micudasoftware.presentation.agenda.viewmodel

import com.micudasoftware.presentation.common.UIEvent

/**
 * Events for the Agenda screen.
 */
sealed class AgendaScreenEvent: UIEvent {

    /**
     * Event to refresh the agenda screen.
     */
    data object Refresh: AgendaScreenEvent()

    /**
     * Event to select a specific month.
     *
     * @param month The month to be selected (1-12).
     */
    data class SelectMonth(val month: Int): AgendaScreenEvent()

    /**
     * Event to select a specific year.
     *
     * @param year The year to be selected.
     */
    data class SelectYear(val year: Int): AgendaScreenEvent()

    /**
     * Event to remove a task.
     *
     * @param id The ID of the task to be removed.
     */
    data class RemoveTask(val id: Long): AgendaScreenEvent()

    /**
     * Event to change the done state of a task.
     *
     * @param id The ID of the task.
     * @param isDone The new done state of the task.
     */
    data class ChangeDoneState(val id: Long, val isDone: Boolean) : AgendaScreenEvent()
}