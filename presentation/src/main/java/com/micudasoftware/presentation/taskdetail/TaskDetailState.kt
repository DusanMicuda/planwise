package com.micudasoftware.presentation.taskdetail

import androidx.compose.ui.graphics.Color
import com.micudasoftware.data.repository.model.Task
import com.micudasoftware.presentation.taskdetail.components.model.CategoryModel
import com.micudasoftware.presentation.common.UIState
import com.micudasoftware.presentation.taskdetail.components.model.DateTimeModel
import com.micudasoftware.presentation.taskdetail.components.model.ReminderModel
import java.time.OffsetDateTime

data class TaskDetailState(
    val id: Long? = null,
    val title: String = "",
    val description: String = "",
    val category: CategoryModel = CategoryModel(0, "", Color.White),
    val startDateTime: DateTimeModel = DateTimeModel(OffsetDateTime.now()),
    val endDateTime: DateTimeModel = DateTimeModel(OffsetDateTime.now()),
    val isCompleted : Boolean = false,
    val reminders: List<ReminderModel> = emptyList(),
    val isEditable: Boolean = false,
): UIState {

    companion object {

        fun fromTask(task: Task, categoryModel: CategoryModel) = TaskDetailState(
            id = task.id,
            title = task.name,
            description = task.description,
            category = categoryModel,
            startDateTime = DateTimeModel(task.startDateTime),
            endDateTime = DateTimeModel(task.endDateTime),
            reminders = task.reminders.map { ReminderModel(it.toString()) }, // Todo: Format the reminders
            isEditable = false,
        )
    }

    fun toTask() = Task(
        id = id,
        name = title,
        description = description,
        categoryId = category.id,
        startDateTime = startDateTime.offsetDateTime,
        endDateTime = endDateTime.offsetDateTime,
        isCompleted = isCompleted,
        reminders = reminders.map { OffsetDateTime.now() }, // Todo: Format the reminders
    )
}
