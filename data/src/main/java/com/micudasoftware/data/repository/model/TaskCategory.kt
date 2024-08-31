package com.micudasoftware.data.repository.model

import com.micudasoftware.data.database.TaskCategoryEntity

/**
 * Represents a task category in the repository layer.
 *
 * @property id The unique identifier for the task category.
 * @property name The name of the task category.
 * @property color The color associated with the task category.
 */
data class TaskCategory(
    val id: Long,
    val name: String,
    val color: Int
) {
    internal companion object {

        /**
         * Converts a TaskCategoryEntity to a TaskCategory.
         *
         * @param entity The TaskCategoryEntity to convert.
         * @return The converted TaskCategory.
         */
        fun fromEntity(entity: TaskCategoryEntity): TaskCategory {
            return TaskCategory(
                id = entity.id,
                name = entity.name,
                color = entity.color
            )
        }
    }

    /**
     * Converts a TaskCategory to a TaskCategoryEntity.
     *
     * @return The converted TaskCategoryEntity.
     */
    internal fun toEntity(): TaskCategoryEntity {
        return TaskCategoryEntity(
            id = id,
            name = name,
            color = color
        )
    }
}
