package com.micudasoftware.presentation.agenda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micudasoftware.data.repository.TasksRepository
import com.micudasoftware.presentation.agenda.component.model.AgendaDayModel
import com.micudasoftware.presentation.agenda.component.model.AgendaItemModel
import com.micudasoftware.presentation.common.ComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * ViewModel for managing the state and events of the Agenda screen.
 *
 * @param repository The repository to access tasks data.
 */
@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val repository: TasksRepository,
) : ViewModel(), ComposeViewModel<AgendaScreenState, AgendaScreenEvent> {

    private val timber = Timber.tag("AgendaViewModel")

    private val _viewState = MutableStateFlow(AgendaScreenState())
    override val viewState = _viewState.asStateFlow()

    init {
        val currentDate = OffsetDateTime.now()
        loadDays(currentDate)
        loadTasks(currentDate)
    }

    override fun onEvent(event: AgendaScreenEvent) {
        when(event) {
            AgendaScreenEvent.Refresh -> loadTasks(viewState.value.date)
            is AgendaScreenEvent.SelectYear -> selectYear(event.year)
            is AgendaScreenEvent.SelectMonth -> selectMonth(event.month)
            is AgendaScreenEvent.ChangeDoneState -> changeDoneState(event.id, event.isDone)
            is AgendaScreenEvent.RemoveTask -> removeTask(event.id)
        }
    }

    /**
     * Loads the days of the current month.
     *
     * @param date The current date.
     */
    private fun loadDays(date: OffsetDateTime) {
        val dayFormatter = DateTimeFormatter.ofPattern("eee")

        val firstDayOfMonth = date.withDayOfMonth(1)
        val lastDayOfMonth = date.withDayOfMonth(date.toLocalDate().lengthOfMonth())
        val days = (firstDayOfMonth.dayOfMonth..lastDayOfMonth.dayOfMonth).map { day ->
            val currentDate = date.withDayOfMonth(day)
            val dayString = currentDate.format(dayFormatter)
            AgendaDayModel(
                dayString = dayString,
                dayNumber = day,
                selected = date.dayOfMonth == day,
                onClick = {
                    _viewState.update {
                        it.copy(days = it.days.map { dayModel ->
                            dayModel.copy(selected = dayModel.dayNumber == day)
                        })
                    }
                    loadTasks(currentDate)
                }
            )
        }

        _viewState.update { it.copy(days = days,) }
    }

    /**
     * Loads the tasks for the specified date.
     *
     * @param date The date for which to load tasks.
     */
    private fun loadTasks(date: OffsetDateTime) {
        viewModelScope.launch {
            val tasks = repository.getTasksForDay(date)
            val categories = repository.getTaskCategories()

            _viewState.update { state ->
                state.copy(
                    tasks = tasks.map { task ->
                        AgendaItemModel.fromTask(
                            task = task,
                            category = categories.firstOrNull { it.id == task.categoryId },
                            currentDate = date
                        )
                    }
                )
            }
        }
    }

    /**
     * Selects a specific month for the agenda.
     *
     * @param month The month to select.
     */
    private fun selectMonth(month: Int) {
        _viewState.update {
            it.copy(months = it.months.map { monthModel ->
                monthModel.copy(selected = monthModel.value == month)
            })
        }
        loadDays(viewState.value.date)
        loadTasks(viewState.value.date)
    }

    /**
     * Selects a specific year for the agenda.
     *
     * @param year The year to select.
     */
    private fun selectYear(year: Int) {
        _viewState.update {
            it.copy(years = it.years.map { yearModel ->
                yearModel.copy(selected = yearModel.year == year)
            })
        }
        loadDays(viewState.value.date)
        loadTasks(viewState.value.date)
    }

    /**
     * Changes the done state of a task.
     *
     * @param id The ID of the task.
     * @param isDone The new done state of the task.
     */
    private fun changeDoneState(id: Long, isDone: Boolean) {
        viewModelScope.launch {
            try {
                val task = repository.getTaskById(id) ?: return@launch
                repository.saveTask(task.copy(isCompleted = isDone))
            } catch (e: Exception) {
                timber.e(e, "Error completing task with id $id")
            }
            loadTasks(viewState.value.date)
        }
    }

    /**
     * Removes a task.
     *
     * @param id The ID of the task to remove.
     */
    private fun removeTask(id: Long) {
        viewModelScope.launch {
            try {
                repository.deleteTaskById(id)
            } catch (e: Exception) {
                timber.e(e, "Error deleting task with id $id")
            }
            loadTasks(viewState.value.date)
        }
    }
}