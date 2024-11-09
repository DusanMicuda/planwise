package com.micudasoftware.presentation.taskdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micudasoftware.data.repository.TasksRepository
import com.micudasoftware.data.repository.model.TaskCategory
import com.micudasoftware.presentation.R
import com.micudasoftware.presentation.common.ComposeViewModel
import com.micudasoftware.presentation.categories.componets.model.CategoryModel
import com.micudasoftware.presentation.common.navigation.Navigator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * ViewModel for the Task Detail screen.
 *
 * @property taskId The ID of the task being viewed or edited.
 * @property navigator The navigator for handling navigation events.
 * @property repository The repository for accessing task data.
 */
@HiltViewModel(assistedFactory = TaskDetailViewModel.Factory::class)
class TaskDetailViewModel @AssistedInject constructor(
    @Assisted edit: Boolean,
    @Assisted private val taskId: Long?,
    private val navigator: Navigator,
    private val repository: TasksRepository
) : ViewModel(), ComposeViewModel<TaskDetailState, TaskDetailEvent> {

    /**
     * Factory interface for creating instances of TaskDetailViewModel.
     */
    @AssistedFactory
    interface Factory {
        fun create(taskId: Long?, edit: Boolean): TaskDetailViewModel
    }

    private val timber = Timber.tag("TaskDetailViewModel")

    private val _viewState = MutableStateFlow(TaskDetailState())
    override val viewState = _viewState.asStateFlow()

    private var categories: List<TaskCategory> = emptyList()

    init {
        viewModelScope.launch {
            updateCategories()
            taskId?.let { loadTask(taskId, edit) }
        }
    }

    /**
     * Handles events from the UI.
     *
     * @param event The event to handle.
     */
    override fun onEvent(event: TaskDetailEvent) {
        when (event) {
            TaskDetailEvent.EditTask -> _viewState.update { it.copy(isEditable = true) }
            TaskDetailEvent.SaveTask -> saveTask()
            TaskDetailEvent.DeleteTask -> deleteTask()
            TaskDetailEvent.CancelEdit -> cancelEdit()
            TaskDetailEvent.ConfirmDialog ->
                _viewState.update { it.copy(alertDialogTextRes = null) }

            is TaskDetailEvent.ToggleDoneState ->
                _viewState.update { it.copy(isCompleted = event.isDone) }

            is TaskDetailEvent.ChangeCategory ->
                _viewState.update { it.copy(category = event.category) }

            is TaskDetailEvent.ChangeTitle -> _viewState.update { it.copy(title = event.title) }

            is TaskDetailEvent.ChangeDescription ->
                _viewState.update { it.copy(description = event.description) }

            is TaskDetailEvent.ChangeStartTime -> _viewState.update {
                it.copy(startDateTime = it.startDateTime.copy(time = event.time))
            }

            is TaskDetailEvent.ChangeStartDate -> _viewState.update {
                it.copy(startDateTime = it.startDateTime.copy(date = event.date))
            }

            is TaskDetailEvent.ChangeEndTime -> _viewState.update {
                it.copy(endDateTime = it.endDateTime.copy(time = event.time))
            }

            is TaskDetailEvent.ChangeEndDate -> _viewState.update {
                it.copy(endDateTime = it.endDateTime.copy(date = event.date))
            }

            is TaskDetailEvent.AddReminder ->
                _viewState.update { it.copy(reminders = it.reminders + event.reminder) }

            is TaskDetailEvent.CreateCategory -> createCategory(event.category)

            is TaskDetailEvent.RemoveReminder -> _viewState.update { state ->
                state.copy(reminders = state.reminders.filter { it != event.reminder })
            }
        }
    }

    /**
     * Cancels the edit operation and reverts changes.
     */
    private fun cancelEdit() {
        taskId?.let {
            _viewState.update { it.copy(isEditable = false) }
            loadTask(taskId)
        } ?: viewModelScope.launch { navigator.navigateUp() }
    }

    /**
     * Saves the current task.
     */
    private fun saveTask() {
        viewModelScope.launch {
            when {
                viewState.value.title.isBlank() -> {
                    _viewState.update { it.copy(alertDialogTextRes = R.string.text_title_is_empty) }
                    return@launch
                }

                viewState.value.category == null -> {
                    _viewState.update { it.copy(alertDialogTextRes = R.string.text_category_is_empty) }
                    return@launch
                }

                viewState.value.endDateTime.offsetDateTime < viewState.value.startDateTime.offsetDateTime -> {
                    _viewState.update { it.copy(alertDialogTextRes = R.string.text_end_date_is_before_start_date) }
                    return@launch
                }
            }

            repository.saveTask(_viewState.value.toTask())
            navigator.navigateUp()
        }
    }

    /**
     * Deletes the current task.
     */
    private fun deleteTask() {
        viewModelScope.launch {
            _viewState.value.toTask().let {
                repository.deleteTask(it)
            }
            navigator.navigateUp()
        }
    }

    /**
     * Creates a new category.
     *
     * @param category The category to create.
     */
    private fun createCategory(category: CategoryModel) {
        viewModelScope.launch {
            repository.saveTaskCategory(category.toTaskCategory())
            updateCategories()
        }
    }

    /**
     * Loads the task with the given ID.
     *
     * @param taskId The ID of the task to load.
     */
    private fun loadTask(taskId: Long, editable: Boolean = false) {
        viewModelScope.launch {
            val task = repository.getTaskById(taskId)
            if (task == null) {
                timber.w("Task with id $taskId not found")
                return@launch
            }

            val category = categories.find { it.id == task.categoryId }
            if (category == null) {
                timber.w("Category with id ${task.categoryId} not found")
                return@launch
            }

            _viewState.update {
                TaskDetailState.fromTask(
                    task,
                    CategoryModel.fromTaskCategory(category),
                    editable
                )
            }
        }
    }

    /**
     * Updates the list of categories.
     */
    private suspend fun updateCategories() {
        categories = repository.getTaskCategories()
        _viewState.update { state ->
            state.copy(categories = categories.map { CategoryModel.fromTaskCategory(it) })
        }
    }
}