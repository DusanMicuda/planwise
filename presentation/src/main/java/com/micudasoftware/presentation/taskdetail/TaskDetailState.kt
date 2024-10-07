package com.micudasoftware.presentation.taskdetail

import androidx.annotation.StringRes
import com.micudasoftware.data.repository.model.Task
import com.micudasoftware.presentation.categories.componets.model.CategoryModel
import com.micudasoftware.presentation.common.UIState
import com.micudasoftware.presentation.taskdetail.components.model.DateTimeModel
import com.micudasoftware.presentation.taskdetail.components.model.ReminderModel
import java.time.OffsetDateTime

/**
 * Data class representing the state of the Task Detail screen.
 *
 * @property id The unique identifier of the task.
 * @property title The title of the task.
 * @property description The description of the task.
 * @property category The category of the task.
 * @property categories The list of all available categories.
 * @property startDateTime The start date and time of the task.
 * @property endDateTime The end date and time of the task.
 * @property isCompleted Indicates whether the task is completed.
 * @property reminders The list of reminders for the task.
 * @property isEditable Indicates whether the task is editable.
 * @property alertDialogTextRes The resource ID for the alert dialog text.
 */
data class TaskDetailState(
    val id: Long? = null,
    val title: String = "",
    val description: String = "",
    val category: CategoryModel? = null,
    val categories: List<CategoryModel> = emptyList(),
    val startDateTime: DateTimeModel = DateTimeModel(OffsetDateTime.now()),
    val endDateTime: DateTimeModel = DateTimeModel(OffsetDateTime.now()),
    val isCompleted : Boolean = false,
    val reminders: List<ReminderModel> = emptyList(),
    val isEditable: Boolean = true,
    @StringRes val alertDialogTextRes: Int? = null,
): UIState {

    companion object {

        /**
         * Creates a [TaskDetailState] from a [Task] and a [CategoryModel].
         *
         * @param task The task to convert.
         * @param categoryModel The category model associated with the task.
         * @return A TaskDetailState representing the task.
         */
        fun fromTask(task: Task, categoryModel: CategoryModel) = TaskDetailState(
            id = task.id,
            title = task.name,
            description = task.description,
            category = categoryModel,
            startDateTime = DateTimeModel(task.startDateTime),
            endDateTime = DateTimeModel(task.endDateTime),
            reminders = task.reminders.map {
                ReminderModel.fromDateTimeDifference(
                    startDateTime = task.startDateTime,
                    reminderDateTime = it
                )
            },
            isEditable = false,
        )
    }

    /**
     * Converts the [TaskDetailState] to a [Task].
     *
     * @return A [Task] representing the current state.
     */
    fun toTask() = Task(
        id = id,
        name = title,
        description = description,
        categoryId = category?.id ?: 0,
        startDateTime = startDateTime.offsetDateTime,
        endDateTime = endDateTime.offsetDateTime,
        isCompleted = isCompleted,
        reminders = reminders.map { it.toOffsetDateTime(startDateTime.offsetDateTime) },
    )
}