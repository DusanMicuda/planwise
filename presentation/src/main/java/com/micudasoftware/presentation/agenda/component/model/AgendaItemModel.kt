package com.micudasoftware.presentation.agenda.component.model

import androidx.compose.ui.graphics.Color
import com.micudasoftware.data.repository.model.Task
import com.micudasoftware.data.repository.model.TaskCategory
import com.micudasoftware.presentation.common.utils.toFormatedDateTime
import com.micudasoftware.presentation.common.utils.toFormatedTime
import java.time.OffsetDateTime

/**
 * Represents a model for an item in the agenda.
 *
 * @property id The unique identifier of the item.
 * @property title The title of the item.
 * @property description The description of the item.
 * @property time The time of the item.
 * @property isDone Indicates whether the task is done.
 * @property color The color of the item.
 */
data class AgendaItemModel(
    val id: Long,
    val title: String,
    val description: String,
    val time: String,
    val isDone: Boolean,
    val color: Color,
) {
    companion object {
        /**
         * Creates an [AgendaItemModel] from a [Task] and a [TaskCategory].
         *
         * @param task The task to convert.
         * @param category The category associated with the task.
         * @param currentDate The current date.
         * @return An [AgendaItemModel] representing the task.
         */
        fun fromTask(task: Task, category: TaskCategory?, currentDate: OffsetDateTime): AgendaItemModel {
            val formattedDateTime = if (
                task.startDateTime.toLocalDate() == currentDate.toLocalDate() &&
                task.endDateTime.toLocalDate() == currentDate.toLocalDate()) {
                "${task.startDateTime.toFormatedTime()} - ${task.endDateTime.toFormatedTime()}"
            } else {
                "${task.startDateTime.toFormatedDateTime()} - ${task.endDateTime.toFormatedDateTime()}"
            }

            return AgendaItemModel(
                id = task.id ?: 0,
                title = task.name,
                description = task.description,
                time = formattedDateTime,
                isDone = task.isCompleted,
                color = category?.let { Color(it.color) } ?: Color.White,
            )
        }
    }
}