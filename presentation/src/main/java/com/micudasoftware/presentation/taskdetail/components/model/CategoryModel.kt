package com.micudasoftware.presentation.taskdetail.components.model

import androidx.compose.ui.graphics.Color

/**
 * Model for a category of task.
 *
 * @param id The id of the category.
 * @param name The name of the category.
 * @param color The color of the category.
 */
data class CategoryModel(
    val id: Long,
    val name: String,
    val color: Color,
)
