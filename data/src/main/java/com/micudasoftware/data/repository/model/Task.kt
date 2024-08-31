package com.micudasoftware.data.repository.model

import com.micudasoftware.data.database.TaskEntity
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

/**
 * Represents a task in the repository layer.
 *
 * @property id The unique identifier for the task.
 * @property name The name of the task.
 * @property description A detailed description of the task.
 * @property categoryId The ID of the category to which the task belongs.
 * @property startDateTime The start date and time of the task.
 * @property endDateTime The end date and time of the task.
 * @property reminders A list of reminder times for the task.
 * @property isCompleted Indicates whether the task is completed.
 */
data class Task(
    val id: Long? = null,
    val name: String,
    val description: String,
    val categoryId: Long,
    val startDateTime: OffsetDateTime,
    val endDateTime: OffsetDateTime,
    val reminders: List<OffsetDateTime>,
    val isCompleted: Boolean
) {
    internal companion object {

        /**
         * Converts a TaskEntity to a Task.
         *
         * @param entity The TaskEntity to convert.
         * @return The converted Task.
         */
        fun fromEntity(entity: TaskEntity): Task {
            return Task(
                id = entity.id,
                name = entity.title,
                description = entity.description,
                categoryId = entity.categoryId,
                startDateTime = entity.startDate.millisecondsToOffsetDateTime(),
                endDateTime = entity.endDate.millisecondsToOffsetDateTime(),
                reminders = entity.reminders.map { it.millisecondsToOffsetDateTime() },
                isCompleted = entity.isCompleted
            )
        }

        /**
         * Converts milliseconds to OffsetDateTime.
         *
         * @receiver The milliseconds to convert.
         * @return The converted OffsetDateTime.
         */
        private fun Long.millisecondsToOffsetDateTime(): OffsetDateTime {
            return Instant
                .ofEpochMilli(this)
                .atZone(ZoneId.systemDefault())
                .toOffsetDateTime()
        }
    }

    /**
     * Converts a Task to a TaskEntity.
     *
     * @return The converted TaskEntity.
     */
    internal fun toEntity(): TaskEntity {
        return TaskEntity(
            id = id ?: 0,
            title = name,
            description = description,
            categoryId = categoryId,
            startDate = startDateTime.toInstant().toEpochMilli(),
            endDate = endDateTime.toInstant().toEpochMilli(),
            reminders = reminders.map { it.toInstant().toEpochMilli() },
            isCompleted = isCompleted
        )
    }
}
