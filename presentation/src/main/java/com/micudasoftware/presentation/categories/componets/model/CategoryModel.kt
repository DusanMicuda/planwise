package com.micudasoftware.presentation.categories.componets.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.micudasoftware.data.repository.model.TaskCategory

/**
 * Model for a category of task.
 *
 * @param id The id of the category.
 * @param name The name of the category.
 * @param color The color of the category.
 */
data class CategoryModel(
    val id: Long = 0,
    val name: String,
    val color: Color,
) {
    companion object {
        /**
         * Creates a [CategoryModel] from a [TaskCategory].
         *
         * @param taskCategory The task category to convert.
         * @return A CategoryModel representing the task category.
         */
        fun fromTaskCategory(taskCategory: TaskCategory) = CategoryModel(
            id = taskCategory.id,
            name = taskCategory.name,
            color = Color(taskCategory.color),
        )
    }

    /**
     * Converts the [CategoryModel] to a [TaskCategory].
     *
     * @return A TaskCategory representing the current category model.
     */
    fun toTaskCategory() = TaskCategory(
        id = id,
        name = name,
        color = color.toArgb(),
    )
}
