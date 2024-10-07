package com.micudasoftware.presentation.taskdetail

import com.micudasoftware.presentation.common.UIEvent
import com.micudasoftware.presentation.categories.componets.model.CategoryModel
import com.micudasoftware.presentation.taskdetail.components.model.ReminderModel
import java.time.OffsetDateTime
import java.time.OffsetTime

/**
 * Sealed class representing events that can occur in the Task Detail screen.
 */
sealed class TaskDetailEvent: UIEvent {

    /**
     * Event to indicate that the task is being edited.
     */
    data object EditTask: TaskDetailEvent()

    /**
     * Event to indicate that the task should be saved.
     */
    data object SaveTask: TaskDetailEvent()

    /**
     * Event to indicate that the task should be deleted.
     */
    data object DeleteTask: TaskDetailEvent()

    /**
     * Event to indicate that the task editing should be canceled.
     */
    data object CancelEdit: TaskDetailEvent()

    /**
     * Event to confirm a dialog action.
     */
    data object ConfirmDialog: TaskDetailEvent()

    /**
     * Event to toggle the done state of the task.
     *
     * @param isDone Boolean indicating whether the task is done.
     */
    data class ToggleDoneState(val isDone: Boolean): TaskDetailEvent()

    /**
     * Event to change the category of the task.
     *
     * @param category The new category for the task.
     */
    data class ChangeCategory(val category: CategoryModel): TaskDetailEvent()

    /**
     * Event to change the title of the task.
     *
     * @param title The new title for the task.
     */
    data class ChangeTitle(val title: String): TaskDetailEvent()

    /**
     * Event to change the description of the task.
     *
     * @param description The new description for the task.
     */
    data class ChangeDescription(val description: String): TaskDetailEvent()

    /**
     * Event to change the start time of the task.
     *
     * @param time The new start time for the task.
     */
    data class ChangeStartTime(val time: OffsetTime): TaskDetailEvent()

    /**
     * Event to change the start date of the task.
     *
     * @param date The new start date for the task.
     */
    data class ChangeStartDate(val date: OffsetDateTime): TaskDetailEvent()

    /**
     * Event to change the end time of the task.
     *
     * @param time The new end time for the task.
     */
    data class ChangeEndTime(val time: OffsetTime): TaskDetailEvent()

    /**
     * Event to change the end date of the task.
     *
     * @param date The new end date for the task.
     */
    data class ChangeEndDate(val date: OffsetDateTime): TaskDetailEvent()

    /**
     * Event to add a reminder to the task.
     *
     * @param reminder The reminder to be added.
     */
    data class AddReminder(val reminder: ReminderModel): TaskDetailEvent()

    /**
     * Event to create a new task category.
     *
     * @param category The category to be created.
     */
    data class CreateCategory(val category: CategoryModel) : TaskDetailEvent()

    /**
     * Event to remove a reminder from the task.
     *
     * @param reminder The reminder to be removed.
     */
    data class RemoveReminder(val reminder: ReminderModel): TaskDetailEvent()
}