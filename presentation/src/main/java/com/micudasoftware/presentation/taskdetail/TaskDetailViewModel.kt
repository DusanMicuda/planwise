package com.micudasoftware.presentation.taskdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micudasoftware.data.repository.TasksRepository
import com.micudasoftware.presentation.common.ComposeViewModel
import com.micudasoftware.presentation.categories.componets.model.CategoryModel
import com.micudasoftware.presentation.taskdetail.components.model.ReminderModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.OffsetTime

@HiltViewModel(assistedFactory = TaskDetailViewModel.Factory::class)
class TaskDetailViewModel @AssistedInject constructor(
    @Assisted taskId: Long?,
    private val repository: TasksRepository
) : ViewModel(), ComposeViewModel<TaskDetailState, TaskDetailEvent> {

    @AssistedFactory
    interface Factory {
        fun create(taskId: Long?): TaskDetailViewModel
    }

    private val _viewState = MutableStateFlow(TaskDetailState())
    override val viewState = _viewState.asStateFlow()

    override fun onEvent(event: TaskDetailEvent) {
        when(event) {
            TaskDetailEvent.EditTask -> editTask()
            TaskDetailEvent.SaveTask -> saveTask()
            TaskDetailEvent.DeleteTask -> deleteTask()
            TaskDetailEvent.CancelEdit -> cancelEdit()
            TaskDetailEvent.NavigateBack -> TODO()
            is TaskDetailEvent.ToggleDoneState -> toggleDoneState(event.isDone)
            is TaskDetailEvent.ChangeCategory -> changeCategory(event.category)
            is TaskDetailEvent.ChangeTitle -> changeTitle(event.title)
            is TaskDetailEvent.ChangeDescription -> changeDescription(event.description)
            is TaskDetailEvent.ChangeStartTime -> changeStartTime(event.time)
            is TaskDetailEvent.ChangeStartDate -> changeStartDate(event.date)
            is TaskDetailEvent.ChangeEndTime -> changeEndTime(event.time)
            is TaskDetailEvent.ChangeEndDate -> changeEndDate(event.date)
            is TaskDetailEvent.AddReminder -> addReminder(event.reminder)
            is TaskDetailEvent.RemoveReminder -> removeReminder(event.reminder)
        }
    }

    private fun editTask() {
        _viewState.update { it.copy(isEditable = true) }
    }

    private fun cancelEdit() {
        _viewState.update { it.copy(isEditable = false) }
        // Load task again
    }

    private fun saveTask() {
        viewModelScope.launch {
            // Todo validate form
            repository.saveTask(_viewState.value.toTask())
        }
    }

    private fun deleteTask() {
        viewModelScope.launch {
            _viewState.value.toTask().let {
                repository.deleteTask(it) // Todo delete task by id
            }
        }
    }

    private fun toggleDoneState(isDone: Boolean) {
        _viewState.update { it.copy(isCompleted = isDone) }
        viewModelScope.launch {
            repository.saveTask(_viewState.value.toTask())
        }
    }

    private fun changeCategory(category: CategoryModel) {
        _viewState.update { it.copy(category = category) }
    }

    private fun changeTitle(title: String) {
        _viewState.update { it.copy(title = title) }
    }

    private fun changeDescription(description: String) {
        _viewState.update { it.copy(description = description) }
    }

    private fun changeStartTime(time: OffsetTime) {
        _viewState.update { it.copy(startDateTime = it.startDateTime.copy(time = time)) }
    }

    private fun changeStartDate(date: OffsetDateTime) {
        _viewState.update { it.copy(startDateTime = it.startDateTime.copy(date = date)) }
    }

    private fun changeEndTime(time: OffsetTime) {
        _viewState.update { it.copy(endDateTime = it.endDateTime.copy(time = time)) }
    }

    private fun changeEndDate(date: OffsetDateTime) {
        _viewState.update { it.copy(endDateTime = it.endDateTime.copy(date = date)) }
    }

    private fun addReminder(reminder: ReminderModel) {
        _viewState.update { state ->
            state.copy(reminders = state.reminders + reminder)
        }
    }

    private fun removeReminder(reminder: ReminderModel) {
        _viewState.update { state ->
            state.copy(reminders = state.reminders.filter { it != reminder })
        }
    }

}